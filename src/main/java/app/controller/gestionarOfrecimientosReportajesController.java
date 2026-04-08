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
        
        view.addFiltroEstadoListener(e -> cargarTabla());
        view.addChkEspecializacionListener(e -> cargarComboTematicas()); 
        view.addComboTematicasListener(e -> cargarTabla()); 
        
        view.addAplicarFiltrosListener(e -> cargarTabla());
        view.addLimpiarFiltrosListener(e -> limpiarFiltros()); 
        
        // NUEVO LISTENER HU 34351
        view.addChkFiltroEmbargoListener(e -> cargarTabla());
        
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

    private void limpiarFiltros() {
        view.getTxtPrecioMin().setText("");
        view.getTxtPrecioMax().setText("");
        view.getChkFiltroEmbargo().setSelected(false); // Limpiamos el checkbox nuevo
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

        // Leemos el estado del checkbox
        boolean soloEmbargosActivos = view.getChkFiltroEmbargo().isSelected();

        // Llamamos al modelo pasándole el boolean (soluciona el error)
        listaMostrada = model.getOfrecimientosFiltrados(nombreEmpresaActual, estadoFiltro, tematicaSeleccionada, precioMin, precioMax, soloEmbargosActivos);
        
        view.getBtnEliminarDecision().setVisible(!estadoFiltro.equals("PENDIENTE"));
        
        // Añadidas las dos nuevas columnas: "accesoEspecialPantalla", "fechaFinEmbargoPantalla"
        TableModel tmodel = SwingUtil.getTableModelFromPojos(listaMostrada, 
                new String[] {"idEvento", "descripcionEvento", "fechaEvento", "nombreAgencia", "precio", "estado", "accesoVisible", "accesoEspecialPantalla", "fechaFinEmbargoPantalla"});
        
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
        boolean tieneAccesoTotal = dtoSeleccionado.getTieneAcceso() != null && dtoSeleccionado.getTieneAcceso() == 1;
        boolean tieneAccesoEspecial = dtoSeleccionado.getAccesoEspecialEmbargo() != null && dtoSeleccionado.getAccesoEspecialEmbargo() == 1;

        // Si tiene el acceso total concedido (pero no el especial de embargo), bloqueamos botones
        if (estaAceptado && tieneAccesoTotal && !tieneAccesoEspecial) {
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