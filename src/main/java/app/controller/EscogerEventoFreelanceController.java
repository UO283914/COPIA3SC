package app.controller;

import java.util.List;

import javax.swing.JOptionPane;

import app.dto.EscogerEventoFreelanceDTO;
import app.model.EscogerEventoFreelanceModel;
import app.view.EscogerEventoFreelanceView;
import giis.demo.util.SwingUtil;

public class EscogerEventoFreelanceController {

    private EscogerEventoFreelanceModel model;
    private EscogerEventoFreelanceView view;
    private String nombreReportero;
    private List<EscogerEventoFreelanceDTO> eventosDisponibles;

    public EscogerEventoFreelanceController(EscogerEventoFreelanceModel m, EscogerEventoFreelanceView v, String nombreReportero) {
        this.model = m;
        this.view = v;
        this.nombreReportero = nombreReportero;
        initView();
        initController();
    }

    private void initView() {
        view.lblNombreFreelance.setText("Reportero freelance: " + nombreReportero);
        view.lblEspecialidades.setText("Especialidades: " + model.getEspecialidadesReportero(nombreReportero));
        cargarEventos();
        limpiarDetalle();
        view.setVisible(true);
    }

    private void initController() {
        view.tableEventos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                mostrarEventoSeleccionado();
            }
        });

        view.btnGuardarPreferencia.addActionListener(e -> guardarPreferencia());
        view.btnLimpiarSeleccion.addActionListener(e -> {
            view.tableEventos.clearSelection();
            limpiarDetalle();
        });
        view.btnCerrar.addActionListener(e -> view.dispose());
    }

    private void cargarEventos() {
        eventosDisponibles = model.getEventosDisponibles(nombreReportero);
        String[] columnas = { "id_evento", "fecha", "nombre_agencia", "tematicas_evento", "estado_preferencia" };
        view.tableEventos.setModel(SwingUtil.getTableModelFromPojos(eventosDisponibles, columnas));
        SwingUtil.autoAdjustColumns(view.tableEventos);
    }

    private void mostrarEventoSeleccionado() {
        int fila = view.tableEventos.getSelectedRow();
        if (fila == -1) {
            limpiarDetalle();
            return;
        }

        EscogerEventoFreelanceDTO dto = eventosDisponibles.get(fila);
        view.lblAgenciaEvento.setText("Agencia: " + dto.getNombre_agencia());
        view.lblFechaEvento.setText("Fecha: " + dto.getFecha());
        view.lblTematicasEvento.setText("Tematicas: " + dto.getTematicas_evento());
        view.txtDescripcionEvento.setText(dto.getDescripcion());
        seleccionarPreferencia(dto.getEstado_preferencia());
    }

    private void guardarPreferencia() {
        int fila = view.tableEventos.getSelectedRow();
        if (fila == -1) {
            SwingUtil.showMessage("Debes seleccionar un evento.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        EscogerEventoFreelanceDTO dto = eventosDisponibles.get(fila);
        String preferencia = (String) view.comboPreferencia.getSelectedItem();
        model.guardarPreferencia(nombreReportero, dto.getId_evento(), preferencia);
        dto.setEstado_preferencia(preferencia.toUpperCase());
        cargarEventos();
        if (fila < view.tableEventos.getRowCount()) {
            view.tableEventos.setRowSelectionInterval(fila, fila);
        }
        SwingUtil.showMessage("Preferencia guardada correctamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
    }

    private void limpiarDetalle() {
        view.lblAgenciaEvento.setText("Agencia: -");
        view.lblFechaEvento.setText("Fecha: -");
        view.lblTematicasEvento.setText("Tematicas: -");
        view.txtDescripcionEvento.setText("");
        view.comboPreferencia.setSelectedIndex(0);
    }

    private void seleccionarPreferencia(String estadoPreferencia) {
        if (estadoPreferencia == null || estadoPreferencia.isBlank()) {
            view.comboPreferencia.setSelectedIndex(0);
            return;
        }
        switch (estadoPreferencia.toUpperCase()) {
        case "INTERESADO":
            view.comboPreferencia.setSelectedItem("Interesado");
            break;
        case "NO_INTERESADO":
            view.comboPreferencia.setSelectedItem("No interesado");
            break;
        case "DUDANDO":
            view.comboPreferencia.setSelectedItem("Dudando");
            break;
        default:
            view.comboPreferencia.setSelectedIndex(0);
            break;
        }
    }
}
