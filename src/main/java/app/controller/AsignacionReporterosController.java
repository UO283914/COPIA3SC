package app.controller;

import giis.demo.util.SwingUtil;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.TableModel;
import app.dto.EventoDisplayDTO;
import app.dto.ReporteroDisplayDTO;
import app.model.AsignacionReporterosModel;
import app.view.AsignarReporterosView;

public class AsignacionReporterosController {

    private String nombreAgencia;
    private AsignacionReporterosModel model;
    private AsignarReporterosView view;

    private List<ReporteroDisplayDTO> reporterosAsignadosVisualmente;
    private List<ReporteroDisplayDTO> reporterosDisponiblesVisualmente;

    public AsignacionReporterosController(AsignacionReporterosModel m, AsignarReporterosView v, String nombreAgencia) {
        this.model = m;
        this.view = v;
        this.nombreAgencia = nombreAgencia;
        this.reporterosAsignadosVisualmente = new ArrayList<>();
        this.reporterosDisponiblesVisualmente = new ArrayList<>();
        this.initView();
    }

    public void initController() {
        view.getCbFiltroEventos().addActionListener(e -> SwingUtil.exceptionWrapper(() -> cargarEventosPorFiltro()));
        view.getCbFiltroTematicaReporteros().addActionListener(
                e -> SwingUtil.exceptionWrapper(() -> cargarDetallesEvento()));
        view.getCbFiltroTipoReportero().addActionListener(
                e -> SwingUtil.exceptionWrapper(() -> cargarDetallesEvento()));

        view.getTabEventos().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                SwingUtil.exceptionWrapper(() -> cargarDetallesEvento());
            }
        });

        view.getBtnAsignar().addActionListener(e -> SwingUtil.exceptionWrapper(() -> moverReporteroAAsignados()));
        view.getBtnEliminar().addActionListener(e -> SwingUtil.exceptionWrapper(() -> moverReporteroADisponibles()));
        view.getBtnAceptar().addActionListener(e -> SwingUtil.exceptionWrapper(() -> confirmarAsignacion()));
        view.getBtnCancelar().addActionListener(e -> view.getFrame().dispose());
    }

    public void initView() {
        view.getLblTituloAgencia().setText("Agencia de Prensa: " + nombreAgencia);
        cargarEventosPorFiltro();
        view.getFrame().setVisible(true);
    }

    private void cargarEventosPorFiltro() {
        int indiceFiltro = view.getCbFiltroEventos().getSelectedIndex();
        List<EventoDisplayDTO> eventos;

        if (indiceFiltro == 0) {
            eventos = model.getEventosSinAsignar(nombreAgencia);
        } else {
            eventos = model.getEventosConAsignacion(nombreAgencia);
        }

        TableModel tmodel = SwingUtil.getTableModelFromPojos(eventos,
                new String[] { "idEvento", "descripcion", "fecha", "tematicas" });
        view.getTabEventos().setModel(tmodel);
        SwingUtil.autoAdjustColumns(view.getTabEventos());

        view.getTabEventos().getColumnModel().getColumn(0).setMinWidth(0);
        view.getTabEventos().getColumnModel().getColumn(0).setMaxWidth(0);
        view.getTabEventos().getColumnModel().getColumn(0).setWidth(0);
        view.getTabEventos().getColumnModel().getColumn(2).setMinWidth(80);
        view.getTabEventos().getColumnModel().getColumn(2).setMaxWidth(100);
        view.getTabEventos().setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        view.getTabEventos().setRowHeight(25);

        reporterosDisponiblesVisualmente.clear();
        reporterosAsignadosVisualmente.clear();
        actualizarTablaDisponiblesVisualmente();
        actualizarTablaAsignadosVisualmente();
    }

    private void cargarDetallesEvento() {
        int filaSeleccionada = view.getTabEventos().getSelectedRow();
        if (filaSeleccionada >= 0) {
            Integer idEvento = (Integer) view.getTabEventos().getValueAt(filaSeleccionada, 0);
            String fecha = (String) view.getTabEventos().getValueAt(filaSeleccionada, 2);
            boolean soloEspecializados = view.getCbFiltroTematicaReporteros().getSelectedIndex() == 1;
            String tipoReportero = (String) view.getCbFiltroTipoReportero().getSelectedItem();

            reporterosDisponiblesVisualmente = model.getReporterosDisponibles(fecha, nombreAgencia, idEvento,
                    soloEspecializados, tipoReportero);
            reporterosAsignadosVisualmente = model.getReporterosAsignados(idEvento);

            actualizarTablaDisponiblesVisualmente();
            actualizarTablaAsignadosVisualmente();
        }
    }

    private void moverReporteroAAsignados() {
        int filaEvento = view.getTabEventos().getSelectedRow();
        int[] filasReporteros = view.getTabDisponibles().getSelectedRows();

        if (filaEvento == -1 || filasReporteros.length == 0) {
            SwingUtil.showMessage("Debes seleccionar un evento y al menos un reportero disponible.", "Aviso",
                    javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        for (int i = filasReporteros.length - 1; i >= 0; i--) {
            int fila = filasReporteros[i];
            reporterosAsignadosVisualmente.add(reporterosDisponiblesVisualmente.get(fila));
            reporterosDisponiblesVisualmente.remove(fila);
        }
        actualizarTablaDisponiblesVisualmente();
        actualizarTablaAsignadosVisualmente();
    }

    private void moverReporteroADisponibles() {
        int filaEvento = view.getTabEventos().getSelectedRow();
        int[] filasReporteros = view.getTabAsignados().getSelectedRows();

        if (filaEvento == -1 || filasReporteros.length == 0) {
            SwingUtil.showMessage("Debes seleccionar un evento y al menos un reportero asignado para eliminar.", "Aviso",
                    javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        for (int i = filasReporteros.length - 1; i >= 0; i--) {
            int fila = filasReporteros[i];
            reporterosDisponiblesVisualmente.add(reporterosAsignadosVisualmente.get(fila));
            reporterosAsignadosVisualmente.remove(fila);
        }
        actualizarTablaDisponiblesVisualmente();
        actualizarTablaAsignadosVisualmente();
    }

    private void confirmarAsignacion() {
        int filaEvento = view.getTabEventos().getSelectedRow();

        if (filaEvento >= 0) {
            Integer idEvento = (Integer) view.getTabEventos().getValueAt(filaEvento, 0);

            model.eliminarAsignacionesPorEvento(idEvento);

            for (ReporteroDisplayDTO rep : reporterosAsignadosVisualmente) {
                model.guardarAsignacion(idEvento, rep.getIdReportero());
            }

            javax.swing.JOptionPane.showMessageDialog(null, "¡Asignaciones modificadas y guardadas correctamente!");
            view.getFrame().dispose();
        } else {
            javax.swing.JOptionPane.showMessageDialog(null, "Selecciona un evento para confirmar.");
        }
    }

    private void actualizarTablaAsignadosVisualmente() {
        TableModel tmodel = SwingUtil.getTableModelFromPojos(reporterosAsignadosVisualmente,
                new String[] { "idReportero", "nombre", "tipoReportero", "tematicas" });
        view.getTabAsignados().setModel(tmodel);
        view.getTabAsignados().getColumnModel().getColumn(0).setMinWidth(0);
        view.getTabAsignados().getColumnModel().getColumn(0).setMaxWidth(0);
        view.getTabAsignados().setRowHeight(25);
    }

    private void actualizarTablaDisponiblesVisualmente() {
        TableModel tmodel = SwingUtil.getTableModelFromPojos(reporterosDisponiblesVisualmente,
                new String[] { "idReportero", "nombre", "tipoReportero", "tematicas" });
        view.getTabDisponibles().setModel(tmodel);
        view.getTabDisponibles().getColumnModel().getColumn(0).setMinWidth(0);
        view.getTabDisponibles().getColumnModel().getColumn(0).setMaxWidth(0);
        view.getTabDisponibles().setRowHeight(25);
    }
}
