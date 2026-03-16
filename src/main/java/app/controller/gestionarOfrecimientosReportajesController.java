package app.controller;

import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;

import app.dto.gestionarOfrecimientosReportajesDTO;
import app.model.gestionarOfrecimientosReportajesModel;
import app.view.gestionarOfrecimientosReportajesView;
import giis.demo.util.SwingUtil;

public class gestionarOfrecimientosReportajesController {

    private gestionarOfrecimientosReportajesModel model;
    private gestionarOfrecimientosReportajesView view;
    private String nombreEmpresaActual;
    
    private List<gestionarOfrecimientosReportajesDTO> listaMostrada; 
    private boolean ajustandoDesplegable = false;

    public gestionarOfrecimientosReportajesController(gestionarOfrecimientosReportajesModel m, gestionarOfrecimientosReportajesView v, String nombreEmpresa) {
        this.model = m;
        this.view = v;
        this.nombreEmpresaActual = nombreEmpresa;
        
        this.initView();
        this.initController();
    }

    private void initController() {
        view.addAceptarListener(e -> tomarDecision("ACEPTADO"));
        view.addRechazarListener(e -> tomarDecision("RECHAZADO"));
        view.addEliminarDecisionListener(e -> tomarDecision("PENDIENTE")); 
        view.addVolverListener(e -> view.getFrame().dispose());
        
        // Listeners de los filtros
        view.addFiltroEstadoListener(e -> cargarTabla());
        view.addChkEspecializacionListener(e -> cargarComboTematicas()); 
        view.addComboTematicasListener(e -> cargarTabla()); // ¡RESTAURADO! Ahora filtra al instante
        
        // Listeners de Precios y Limpieza
        view.addAplicarFiltrosListener(e -> cargarTabla());
        view.addLimpiarFiltrosListener(e -> limpiarFiltros()); // NUEVO
        
        view.getTabOfrecimientos().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) evaluarBloqueoBotones();
            }
        });
    }

    public void initView() {
        view.setNombreEmpresa(nombreEmpresaActual);
        cargarComboTematicas(); 
        view.getFrame().setVisible(true);
    }

    // NUEVO MÉTODO PARA VACIAR TODO
    private void limpiarFiltros() {
        // 1. Vaciamos únicamente las cajas de texto de los precios
        view.getTxtPrecioMin().setText("");
        view.getTxtPrecioMax().setText("");
        
        // 2. Refrescamos la tabla directamente. 
        // Como el estado y la temática no los hemos tocado, la tabla se 
        // recargará manteniendo esos filtros, pero ya sin límite de precio.
        cargarTabla(); 
    }

    private void cargarComboTematicas() {
        ajustandoDesplegable = true; 
        view.getComboTematicas().removeAllItems();
        view.getComboTematicas().addItem("Todas las temáticas");

        List<Object[]> filasBD;
        if (view.getChkEspecializacion().isSelected()) {
            filasBD = model.obtenerTematicasEmpresa(nombreEmpresaActual);
        } else {
            filasBD = model.obtenerTodasLasTematicas();
        }

        for (Object[] fila : filasBD) {
            view.getComboTematicas().addItem((String) fila[0]);
        }
        
        ajustandoDesplegable = false; 
        cargarTabla(); 
    }

    private void cargarTabla() {
        if (ajustandoDesplegable) return; 
        
        // 1. Recoger filtros
        String estadoFiltro = view.getRdbtnPendientes().isSelected() ? "PENDIENTE" : "CON_DECISION";
        String tematicaSeleccionada = (String) view.getComboTematicas().getSelectedItem();
        if (tematicaSeleccionada == null) tematicaSeleccionada = "Todas las temáticas";

        Double precioMin = null;
        Double precioMax = null;
        try {
            String txtMin = view.getTxtPrecioMin().getText().trim();
            if (!txtMin.isEmpty()) precioMin = Double.parseDouble(txtMin);
            
            String txtMax = view.getTxtPrecioMax().getText().trim();
            if (!txtMax.isEmpty()) precioMax = Double.parseDouble(txtMax);
            
        } catch (NumberFormatException e) {
            SwingUtil.showMessage("Por favor, introduce valores numéricos válidos en los precios (Ej: 150.50).", "Error en Filtros", JOptionPane.ERROR_MESSAGE);
            return; 
        }

        // 2. Ejecutar consulta unificada al Modelo
        listaMostrada = model.getOfrecimientosFiltrados(nombreEmpresaActual, estadoFiltro, tematicaSeleccionada, precioMin, precioMax);
        
        view.getBtnEliminarDecision().setVisible(!estadoFiltro.equals("PENDIENTE"));
        
        // 3. Cargar tabla
        TableModel tmodel = SwingUtil.getTableModelFromPojos(listaMostrada, 
                new String[] {"idEvento", "descripcionEvento", "fechaEvento", "nombreAgencia", "precio", "estado", "accesoVisible"});
        
        view.getTabOfrecimientos().setModel(tmodel);
        SwingUtil.autoAdjustColumns(view.getTabOfrecimientos());
        
        evaluarBloqueoBotones(); 
    }

    private void evaluarBloqueoBotones() {
        int filaSel = view.getTabOfrecimientos().getSelectedRow();
        
        if (filaSel == -1 || listaMostrada == null || listaMostrada.size() <= filaSel) {
            view.getBtnAceptar().setEnabled(false);
            view.getBtnRechazar().setEnabled(false);
            view.getBtnEliminarDecision().setEnabled(false);
            return;
        }

        gestionarOfrecimientosReportajesDTO dtoSeleccionado = listaMostrada.get(filaSel);
        boolean estaAceptado = "ACEPTADO".equals(dtoSeleccionado.getEstado());
        boolean tieneAcceso = dtoSeleccionado.getTieneAcceso() != null && dtoSeleccionado.getTieneAcceso() == 1;

        if (estaAceptado && tieneAcceso) {
            view.getBtnAceptar().setEnabled(false);
            view.getBtnRechazar().setEnabled(false);
            view.getBtnEliminarDecision().setEnabled(false);
        } else {
            view.getBtnAceptar().setEnabled(true);
            view.getBtnRechazar().setEnabled(true);
            view.getBtnEliminarDecision().setEnabled(true);
        }
    }

    private void tomarDecision(String nuevaDecision) {
        String idEventoSeleccionado = SwingUtil.getSelectedKey(view.getTabOfrecimientos());
        if (idEventoSeleccionado.isEmpty()) return;

        model.actualizarEstadoOfrecimiento(idEventoSeleccionado, nombreEmpresaActual, nuevaDecision);
        
        if (nuevaDecision.equals("PENDIENTE")) {
            SwingUtil.showMessage("La decisión ha sido eliminada.", "Información", JOptionPane.INFORMATION_MESSAGE);
        } else {
            SwingUtil.showMessage("El ofrecimiento ha sido marcado como: " + nuevaDecision, "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
        cargarTabla();
    }
}