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
    private boolean eventoFinalizadoSeleccionado = false;
    private Integer responsableSeleccionadoId = null;
    private List<Integer> asignadosOriginales = new ArrayList<>();
    private Integer responsableOriginalId = null;

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
        view.getBtnElegirResponsable().addActionListener(e -> SwingUtil.exceptionWrapper(() -> elegirResponsable()));
        view.getBtnFinalizarAsignacion().addActionListener(e -> SwingUtil.exceptionWrapper(() -> confirmarAsignacion()));
        view.getBtnAceptar().addActionListener(e -> view.getFrame().dispose());
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
                new String[] { "idEvento", "descripcion", "fechaInicio", "fechaFin", "tematicas" });
        view.getTabEventos().setModel(tmodel);
        SwingUtil.autoAdjustColumns(view.getTabEventos());

        view.getTabEventos().getColumnModel().getColumn(0).setMinWidth(0);
        view.getTabEventos().getColumnModel().getColumn(0).setMaxWidth(0);
        view.getTabEventos().getColumnModel().getColumn(0).setWidth(0);
        view.getTabEventos().getColumnModel().getColumn(2).setMinWidth(90);
        view.getTabEventos().getColumnModel().getColumn(2).setMaxWidth(120);
        view.getTabEventos().getColumnModel().getColumn(3).setMinWidth(90);
        view.getTabEventos().getColumnModel().getColumn(3).setMaxWidth(120);
        view.getTabEventos().setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        view.getTabEventos().setRowHeight(25);

        reporterosDisponiblesVisualmente.clear();
        reporterosAsignadosVisualmente.clear();
        eventoFinalizadoSeleccionado = false;
        responsableSeleccionadoId = null;
        asignadosOriginales.clear();
        responsableOriginalId = null;
        view.getLblResponsable().setText("Reportero responsable: (sin seleccionar)");
        actualizarTablaDisponiblesVisualmente();
        actualizarTablaAsignadosVisualmente();
        actualizarEstadoEdicion();
    }

    private void cargarDetallesEvento() {
        int filaSeleccionada = view.getTabEventos().getSelectedRow();
        if (filaSeleccionada >= 0) {
            Integer idEvento = (Integer) view.getTabEventos().getValueAt(filaSeleccionada, 0);
            String fechaInicio = (String) view.getTabEventos().getValueAt(filaSeleccionada, 2);
            String fechaFin = (String) view.getTabEventos().getValueAt(filaSeleccionada, 3);
            boolean soloEspecializados = view.getCbFiltroTematicaReporteros().getSelectedIndex() == 1;
            String tipoReportero = (String) view.getCbFiltroTipoReportero().getSelectedItem();

            reporterosDisponiblesVisualmente = model.getReporterosDisponibles(fechaInicio, fechaFin, nombreAgencia,
                    idEvento, soloEspecializados, tipoReportero);
            reporterosAsignadosVisualmente = model.getReporterosAsignados(idEvento);
            eventoFinalizadoSeleccionado = model.isAsignacionFinalizada(idEvento);
            responsableSeleccionadoId = obtenerResponsableDeAsignados(reporterosAsignadosVisualmente);
            asignadosOriginales = obtenerIdsOrdenados(reporterosAsignadosVisualmente);
            responsableOriginalId = responsableSeleccionadoId;
            actualizarLabelResponsable();

            actualizarTablaDisponiblesVisualmente();
            actualizarTablaAsignadosVisualmente();
            actualizarEstadoEdicion();
        }
    }

    private void moverReporteroAAsignados() {
        if (eventoFinalizadoSeleccionado) {
            SwingUtil.showMessage("La asignación está finalizada y no puede modificarse.", "Aviso",
                    javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }
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
        if (responsableSeleccionadoId != null && !contieneReporteroAsignado(responsableSeleccionadoId)) {
            responsableSeleccionadoId = null;
            actualizarLabelResponsable();
        }
    }

    private void moverReporteroADisponibles() {
        if (eventoFinalizadoSeleccionado) {
            SwingUtil.showMessage("La asignación está finalizada y no puede modificarse.", "Aviso",
                    javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }
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
        if (responsableSeleccionadoId != null && !contieneReporteroAsignado(responsableSeleccionadoId)) {
            responsableSeleccionadoId = null;
            actualizarLabelResponsable();
        }
    }

    private void confirmarAsignacion() {
        int filaEvento = view.getTabEventos().getSelectedRow();

        if (filaEvento >= 0) {
            Integer idEvento = (Integer) view.getTabEventos().getValueAt(filaEvento, 0);
            if (model.isAsignacionFinalizada(idEvento)) {
                SwingUtil.showMessage("La asignación ya está finalizada. No se puede modificar.", "Aviso",
                        javax.swing.JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (reporterosAsignadosVisualmente.isEmpty()) {
                SwingUtil.showMessage("Debe haber al menos un reportero asignado para finalizar.", "Aviso",
                        javax.swing.JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!hayAlMenosUnReporteroBase()) {
                SwingUtil.showMessage("Debe haber al menos un reportero de tipo BASE asignado al evento.", "Aviso",
                        javax.swing.JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (responsableSeleccionadoId == null || !contieneReporteroAsignado(responsableSeleccionadoId)) {
                SwingUtil.showMessage(
                        "Debes elegir un reportero responsable desde la zona de responsable.",
                        "Aviso", javax.swing.JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!hayCambiosRespectoAlEstadoOriginal()) {
                SwingUtil.showMessage("No hay cambios pendientes. Modifica la asignación antes de finalizar.", "Aviso",
                        javax.swing.JOptionPane.WARNING_MESSAGE);
                return;
            }

            model.eliminarAsignacionesPorEvento(idEvento);

            for (ReporteroDisplayDTO rep : reporterosAsignadosVisualmente) {
                boolean esResponsable = rep.getIdReportero().equals(responsableSeleccionadoId);
                model.guardarAsignacion(idEvento, rep.getIdReportero(), esResponsable, "ABIERTA");
            }

            model.actualizarFinalizacionEvento(idEvento, responsableSeleccionadoId);

            javax.swing.JOptionPane.showMessageDialog(null,
                    "¡Asignación del evento finalizada correctamente! Ya no se podrán modificar reporteros de este evento.");
            view.getFrame().dispose();
        } else {
            javax.swing.JOptionPane.showMessageDialog(null, "Selecciona un evento para confirmar.");
        }
    }

    private boolean hayAlMenosUnReporteroBase() {
        for (ReporteroDisplayDTO rep : reporterosAsignadosVisualmente) {
            if (rep.getTipoReportero() != null && "BASE".equalsIgnoreCase(rep.getTipoReportero().trim())) {
                return true;
            }
        }
        return false;
    }

    private void actualizarEstadoEdicion() {
        boolean editable = !eventoFinalizadoSeleccionado;
        view.getBtnAsignar().setEnabled(editable);
        view.getBtnEliminar().setEnabled(editable);
        view.getBtnElegirResponsable().setEnabled(editable);
        view.getBtnFinalizarAsignacion().setEnabled(editable);
    }

    private void elegirResponsable() {
        if (eventoFinalizadoSeleccionado) {
            SwingUtil.showMessage("La asignación está finalizada y no puede modificarse.", "Aviso",
                    javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }
        int filaResponsable = view.getTabAsignados().getSelectedRow();
        if (filaResponsable < 0 || filaResponsable >= reporterosAsignadosVisualmente.size()) {
            SwingUtil.showMessage("Selecciona un reportero de la tabla de asignados para marcarlo como responsable.",
                    "Aviso", javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }
        responsableSeleccionadoId = reporterosAsignadosVisualmente.get(filaResponsable).getIdReportero();
        actualizarLabelResponsable();
    }

    private void actualizarLabelResponsable() {
        String nombre = "(sin seleccionar)";
        if (responsableSeleccionadoId != null) {
            for (ReporteroDisplayDTO rep : reporterosAsignadosVisualmente) {
                if (rep.getIdReportero().equals(responsableSeleccionadoId)) {
                    nombre = rep.getNombre();
                    break;
                }
            }
        }
        view.getLblResponsable().setText("Reportero responsable: " + nombre);
    }

    private Integer obtenerResponsableDeAsignados(List<ReporteroDisplayDTO> asignados) {
        for (ReporteroDisplayDTO rep : asignados) {
            if (rep.getEsResponsable() != null && rep.getEsResponsable() == 1) {
                return rep.getIdReportero();
            }
        }
        return null;
    }

    private List<Integer> obtenerIdsOrdenados(List<ReporteroDisplayDTO> lista) {
        List<Integer> ids = new ArrayList<>();
        for (ReporteroDisplayDTO rep : lista) {
            ids.add(rep.getIdReportero());
        }
        ids.sort(Integer::compareTo);
        return ids;
    }

    private boolean hayCambiosRespectoAlEstadoOriginal() {
        List<Integer> actuales = obtenerIdsOrdenados(reporterosAsignadosVisualmente);
        boolean mismosAsignados = actuales.equals(asignadosOriginales);
        boolean mismoResponsable = (responsableSeleccionadoId == null && responsableOriginalId == null)
                || (responsableSeleccionadoId != null && responsableSeleccionadoId.equals(responsableOriginalId));
        return !(mismosAsignados && mismoResponsable);
    }

    private boolean contieneReporteroAsignado(Integer idReportero) {
        for (ReporteroDisplayDTO rep : reporterosAsignadosVisualmente) {
            if (rep.getIdReportero().equals(idReportero)) {
                return true;
            }
        }
        return false;
    }

    private void actualizarTablaAsignadosVisualmente() {
        TableModel tmodel = SwingUtil.getTableModelFromPojos(reporterosAsignadosVisualmente,
                new String[] { "idReportero", "nombre", "tipoReportero", "tematicas" });
        view.getTabAsignados().setModel(tmodel);
        view.getTabAsignados().getColumnModel().getColumn(0).setMinWidth(0);
        view.getTabAsignados().getColumnModel().getColumn(0).setMaxWidth(0);
        view.getTabAsignados().setRowSelectionAllowed(true);
        view.getTabAsignados().setColumnSelectionAllowed(false);
        view.getTabAsignados().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
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
