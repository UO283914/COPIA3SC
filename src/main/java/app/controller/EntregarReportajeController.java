package app.controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import app.dto.EntregarReportajeDTO;
import app.model.EntregarReportajeModel;
import app.view.EntregarReportajeView;
import giis.demo.util.SwingUtil;

public class EntregarReportajeController {

    private static final String FILTRO_PENDIENTES = "Pendientes de entrega";
    private static final String TIPO_IMAGEN = "IMAGEN";
    private static final String TIPO_VIDEO = "VIDEO";
    private static final String ESTADO_BORRADOR = "BORRADOR";
    private static final String ESTADO_DEFINITIVO = "DEFINITIVO";

    private EntregarReportajeModel model;
    private EntregarReportajeView view;
    private String nombreReportero;
    private List<EntregarReportajeDTO> versionesCargadas;

    public EntregarReportajeController(EntregarReportajeModel model, EntregarReportajeView view, String nombreReportero) {
        this.model = model;
        this.view = view;
        this.nombreReportero = nombreReportero;
        this.versionesCargadas = new ArrayList<>();
        initView();
        initController();
    }

    private void initView() {
        recargarEventosSegunFiltro();
        view.setVisible(true);
    }

    private void initController() {
        view.addFiltroListener(e -> recargarEventosSegunFiltro());
        view.addEventoListener(e -> cargarContenidoEventoSeleccionado());
        view.addVersionListener(e -> cargarVersionSeleccionada());
        view.addAceptarListener(e -> aceptarCambios());
        view.addRestaurarVersionListener(e -> restaurarVersionSeleccionada());
        view.addSolicitarRevisionListener(e -> solicitarRevisionReportaje());
        view.addAnadirImagenListener(e -> anadirMultimedia(TIPO_IMAGEN));
        view.addAnadirVideoListener(e -> anadirMultimedia(TIPO_VIDEO));
        view.addCambiarFotoABorradorListener(e -> cambiarEstadoMultimediaSeleccionada(view.getComboImagenesDefinitivas(),
                ESTADO_BORRADOR, "foto"));
        view.addCambiarVideoABorradorListener(e -> cambiarEstadoMultimediaSeleccionada(view.getComboVideosDefinitivos(),
                ESTADO_BORRADOR, "video"));
        view.addCambiarFotoADefinitivaListener(e -> cambiarEstadoMultimediaSeleccionada(view.getComboImagenesBorrador(),
                ESTADO_DEFINITIVO, "foto"));
        view.addCambiarVideoADefinitivoListener(e -> cambiarEstadoMultimediaSeleccionada(view.getComboVideosBorrador(),
                ESTADO_DEFINITIVO, "video"));
        view.addEliminarFotoListener(
                e -> eliminarMultimediaSeleccionada((EntregarReportajeDTO) view.getComboImagenesBorrador().getSelectedItem(),
                        "foto"));
        view.addEliminarVideoListener(
                e -> eliminarMultimediaSeleccionada((EntregarReportajeDTO) view.getComboVideosBorrador().getSelectedItem(),
                        "video"));
    }

    private boolean isModoPendientes() {
        Object item = view.getComboFiltro().getSelectedItem();
        return FILTRO_PENDIENTES.equals(item);
    }

    private void recargarEventosSegunFiltro() {
        view.getComboEventos().removeAllItems();

        List<EntregarReportajeDTO> eventos;
        if (isModoPendientes()) {
            eventos = model.getEventosPendientes(nombreReportero);
            view.setTituloEditable(true);
            view.setContenidoEditable(true);
            view.setMultimediaEditable(true);
            view.setTextoBotonAceptar("Entregar");
            view.setSolicitarRevisionEnabled(false);
            view.setMensajePermisos("");
            view.setSeccionVersionesVisible(false);
            view.limpiarCampos();
        } else {
            eventos = model.getEventosEntregados(nombreReportero);
            view.setTituloEditable(false);
            view.setTextoBotonAceptar("Guardar cambios");
            view.setSolicitarRevisionEnabled(false);
        }

        for (EntregarReportajeDTO evento : eventos) {
            view.getComboEventos().addItem(evento);
        }

        cargarContenidoEventoSeleccionado();
    }

    private void cargarContenidoEventoSeleccionado() {
        EntregarReportajeDTO seleccionado = (EntregarReportajeDTO) view.getComboEventos().getSelectedItem();

        if (seleccionado == null) {
            view.limpiarCampos();
            view.limpiarVersionSeleccionada();
            view.getComboVersiones().removeAllItems();
            view.setTituloEditable(isModoPendientes());
            view.setContenidoEditable(isModoPendientes());
            view.setMultimediaEditable(false);
            view.setSeccionVersionesVisible(false);
            view.setSolicitarRevisionEnabled(false);
            versionesCargadas = new ArrayList<>();
            return;
        }

        if (isModoPendientes()) {
            cargarPendiente(seleccionado);
            return;
        }

        EntregarReportajeDTO reportaje = model.getReportajePorEvento(seleccionado.getId_evento(), nombreReportero);
        if (reportaje == null) {
            view.limpiarCampos();
            view.limpiarVersionSeleccionada();
            view.getComboVersiones().removeAllItems();
            view.setContenidoEditable(false);
            view.setMultimediaEditable(false);
            view.setSeccionVersionesVisible(false);
            view.setSolicitarRevisionEnabled(false);
            view.setMensajePermisos("No se encontró contenido para el evento seleccionado.");
            return;
        }

        view.setTitulo(reportaje.getTitulo());
        view.setSubtitulo(reportaje.getSubtitulo());
        view.setCuerpo(reportaje.getCuerpo());
        view.setTituloEditable(false);

        boolean editableContenido = reportaje.isEditablePorReportero();
        boolean bloqueadoRevision = reportaje.isBloqueadoPorRevision();
        view.setContenidoEditable(editableContenido);
        view.setSeccionVersionesVisible(editableContenido);
        view.setMultimediaEditable(!bloqueadoRevision);
        view.setSolicitarRevisionEnabled(reportaje.puedeSolicitarRevision());
        view.setMensajePermisos(construirMensajePermisosEntregado(reportaje));

        cargarVersiones(reportaje.getId_reportaje());
        cargarMultimedia(reportaje.getId_reportaje());
    }

    private void cargarPendiente(EntregarReportajeDTO seleccionado) {
        EntregarReportajeDTO borrador = model.getBorradorPorEvento(seleccionado.getId_evento(), nombreReportero);
        view.getComboVersiones().removeAllItems();
        view.limpiarVersionSeleccionada();
        view.setTituloEditable(true);
        view.setContenidoEditable(true);
        view.setMultimediaEditable(true);
        view.setSeccionVersionesVisible(false);
        view.setSolicitarRevisionEnabled(false);
        view.setMensajePermisos(
                "En pendientes también puedes añadir y modificar multimedia; el borrador se crea al guardar el primer archivo y cada ruta debe ser única.");

        if (borrador == null || borrador.getId_reportaje() == null) {
            view.setTitulo("");
            view.setSubtitulo("");
            view.setCuerpo("");
            view.limpiarMultimedia();
            return;
        }

        view.setTitulo(borrador.getTitulo());
        view.setSubtitulo(borrador.getSubtitulo());
        view.setCuerpo(borrador.getCuerpo());
        cargarMultimedia(borrador.getId_reportaje());
    }

    private String construirMensajePermisosEntregado(EntregarReportajeDTO reportaje) {
        if (reportaje.isBloqueadoPorRevision()) {
            return "Revisión solicitada: el contenido del reportaje queda bloqueado y no se puede modificar.";
        }
        if (reportaje.isEditablePorReportero()) {
            return "Puedes modificar subtítulo/cuerpo, restaurar versiones, gestionar multimedia y solicitar revisión.";
        }
        return "Puedes añadir multimedia; solo quien lo sube puede cambiar su estado o borrarlo.";
    }

    private void cargarVersiones(int idReportaje) {
        versionesCargadas = model.getVersionesReportaje(idReportaje);
        view.getComboVersiones().removeAllItems();

        for (EntregarReportajeDTO version : versionesCargadas) {
            String etiqueta = version.getFecha_cambio() + " " + version.getHora_cambio() + " - "
                    + version.getResumenCambios();
            view.getComboVersiones().addItem(etiqueta);
        }

        cargarVersionSeleccionada();
    }

    private void cargarMultimedia(int idReportaje) {
        recargarComboMultimedia(view.getComboImagenesDefinitivas(), model.getMultimedia(idReportaje, TIPO_IMAGEN,
                ESTADO_DEFINITIVO, nombreReportero));
        recargarComboMultimedia(view.getComboVideosDefinitivos(), model.getMultimedia(idReportaje, TIPO_VIDEO,
                ESTADO_DEFINITIVO, nombreReportero));
        recargarComboMultimedia(view.getComboImagenesBorrador(), model.getMultimedia(idReportaje, TIPO_IMAGEN,
                ESTADO_BORRADOR, nombreReportero));
        recargarComboMultimedia(view.getComboVideosBorrador(), model.getMultimedia(idReportaje, TIPO_VIDEO,
                ESTADO_BORRADOR, nombreReportero));
    }

    private void recargarComboMultimedia(JComboBox<EntregarReportajeDTO> combo, List<EntregarReportajeDTO> elementos) {
        combo.removeAllItems();
        for (EntregarReportajeDTO elemento : elementos) {
            combo.addItem(elemento);
        }
    }

    private void cargarVersionSeleccionada() {
        if (versionesCargadas == null || versionesCargadas.isEmpty()) {
            view.limpiarVersionSeleccionada();
            return;
        }

        int idx = view.getIndiceVersionSeleccionada();
        if (idx < 0 || idx >= versionesCargadas.size()) {
            view.limpiarVersionSeleccionada();
            return;
        }

        EntregarReportajeDTO version = versionesCargadas.get(idx);
        String subtitulo = version.getSubtitulo_guardado() == null ? "(sin cambio en esta versión)"
                : version.getSubtitulo_guardado();
        String cuerpo = version.getCuerpo_guardado() == null ? "(sin cambio en esta versión)"
                : version.getCuerpo_guardado();

        view.setSubtituloVersion(subtitulo);
        view.setCuerpoVersion(cuerpo);
    }

    private void aceptarCambios() {
        if (isModoPendientes()) {
            entregarNuevoReportaje();
        } else {
            modificarReportajeExistente();
        }
    }

    private void entregarNuevoReportaje() {
        EntregarReportajeDTO eventoSeleccionado = (EntregarReportajeDTO) view.getComboEventos().getSelectedItem();
        if (eventoSeleccionado == null) {
            SwingUtil.showMessage("No tienes eventos pendientes de reportaje.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String titulo = view.getTitulo().trim();
        String subtitulo = view.getSubtitulo().trim();
        String cuerpo = view.getCuerpo().trim();

        if (titulo.isEmpty() || subtitulo.isEmpty() || cuerpo.isEmpty()) {
            SwingUtil.showMessage("Título, subtítulo y cuerpo son obligatorios.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        EntregarReportajeDTO borrador = model.getBorradorPorEvento(eventoSeleccionado.getId_evento(), nombreReportero);
        Integer idReportajeActual = borrador == null ? null : borrador.getId_reportaje();
        if (model.existeTituloEnOtroReportaje(titulo, idReportajeActual)) {
            SwingUtil.showMessage("Ya existe un reportaje con ese título.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (idReportajeActual == null) {
            model.insertarReportaje(eventoSeleccionado.getId_evento(), nombreReportero, titulo, subtitulo, cuerpo);
        } else {
            model.entregarBorrador(idReportajeActual, nombreReportero, titulo, subtitulo, cuerpo);
        }
        SwingUtil.showMessage("Reportaje entregado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        recargarEventosSegunFiltro();
    }

    private void modificarReportajeExistente() {
        EntregarReportajeDTO eventoSeleccionado = (EntregarReportajeDTO) view.getComboEventos().getSelectedItem();
        if (eventoSeleccionado == null) {
            SwingUtil.showMessage("No hay eventos entregados para modificar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        EntregarReportajeDTO actual = model.getReportajePorEvento(eventoSeleccionado.getId_evento(), nombreReportero);
        if (actual == null) {
            SwingUtil.showMessage("No se encontró reportaje para el evento seleccionado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!actual.isEditablePorReportero()) {
            SwingUtil.showMessage("Solo el reportero que hizo la entrega puede modificar este reportaje.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nuevoSubtitulo = view.getSubtitulo().trim();
        String nuevoCuerpo = view.getCuerpo().trim();

        if (nuevoSubtitulo.isEmpty() || nuevoCuerpo.isEmpty()) {
            SwingUtil.showMessage("Subtítulo y cuerpo son obligatorios para modificar.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        registrarActualizacion(actual, nuevoSubtitulo, nuevoCuerpo, "Cambios guardados y versión registrada correctamente.");
    }

    private void anadirMultimedia(String tipo) {
        EntregarReportajeDTO actual = getReportajeSeleccionadoParaMultimedia();
        if (actual == null) {
            return;
        }
        if (isReportajeBloqueadoPorRevision(actual)) {
            SwingUtil.showMessage("No se puede modificar multimedia: el reportaje está en revisión.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String ruta = TIPO_IMAGEN.equals(tipo) ? view.getRutaImagen().trim() : view.getRutaVideo().trim();
        if (ruta.isEmpty()) {
            SwingUtil.showMessage("Debes indicar una ruta antes de añadir el contenido multimedia.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (model.existeRutaMultimedia(ruta)) {
            SwingUtil.showMessage("Esa ruta ya está registrada para otro contenido multimedia.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        model.insertarMultimedia(actual.getId_reportaje(), nombreReportero, tipo, ruta);
        if (TIPO_IMAGEN.equals(tipo)) {
            view.limpiarRutaImagen();
        } else {
            view.limpiarRutaVideo();
        }
        cargarMultimedia(actual.getId_reportaje());
        SwingUtil.showMessage(tipo.equals(TIPO_IMAGEN) ? "Imagen añadida en estado borrador." : "Vídeo añadido en estado borrador.",
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    private void cambiarEstadoMultimediaSeleccionada(JComboBox<EntregarReportajeDTO> combo, String nuevoEstado,
            String etiqueta) {
        EntregarReportajeDTO multimedia = (EntregarReportajeDTO) combo.getSelectedItem();
        if (multimedia == null) {
            SwingUtil.showMessage("Debes seleccionar un " + etiqueta + ".", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        EntregarReportajeDTO actual = getReportajeSeleccionadoParaMultimedia();
        if (isReportajeBloqueadoPorRevision(actual)) {
            SwingUtil.showMessage("No se puede modificar multimedia: el reportaje está en revisión.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!multimedia.isEditableMultimediaPorReportero()) {
            SwingUtil.showMessage("Solo el reportero que añadió este " + etiqueta + " puede cambiar su estado.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        model.actualizarEstadoMultimedia(multimedia.getId_multimedia(), nuevoEstado);
        recargarMultimediaActual();
        SwingUtil.showMessage("Estado del " + etiqueta + " actualizado correctamente.", "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void eliminarMultimediaSeleccionada(EntregarReportajeDTO multimedia, String etiqueta) {
        if (multimedia == null) {
            SwingUtil.showMessage("Debes seleccionar un " + etiqueta + ".", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        EntregarReportajeDTO actual = getReportajeSeleccionadoParaMultimedia();
        if (isReportajeBloqueadoPorRevision(actual)) {
            SwingUtil.showMessage("No se puede modificar multimedia: el reportaje está en revisión.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!multimedia.isEditableMultimediaPorReportero()) {
            SwingUtil.showMessage("Solo el reportero que añadió este " + etiqueta + " puede eliminarlo.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        model.eliminarMultimedia(multimedia.getId_multimedia());
        recargarMultimediaActual();
        SwingUtil.showMessage("" + Character.toUpperCase(etiqueta.charAt(0)) + etiqueta.substring(1)
                + " eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    private void recargarMultimediaActual() {
        EntregarReportajeDTO actual = getReportajeSeleccionadoParaMultimedia();
        if (actual != null) {
            cargarMultimedia(actual.getId_reportaje());
        }
    }

    private EntregarReportajeDTO getReportajeSeleccionadoParaMultimedia() {
        EntregarReportajeDTO eventoSeleccionado = (EntregarReportajeDTO) view.getComboEventos().getSelectedItem();
        if (eventoSeleccionado == null) {
            SwingUtil.showMessage("Debes seleccionar un evento.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        if (isModoPendientes()) {
            EntregarReportajeDTO borrador = model.getBorradorPorEvento(eventoSeleccionado.getId_evento(), nombreReportero);
            if (borrador != null && borrador.getId_reportaje() != null) {
                return borrador;
            }
            model.crearBorradorSiNoExiste(eventoSeleccionado.getId_evento());
            borrador = model.getBorradorPorEvento(eventoSeleccionado.getId_evento(), nombreReportero);
            if (borrador != null && borrador.getId_reportaje() != null) {
                cargarPendiente(eventoSeleccionado);
            }
            if (borrador == null || borrador.getId_reportaje() == null) {
                SwingUtil.showMessage("No se pudo preparar el borrador del reportaje.", "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }
            return borrador;
        }

        EntregarReportajeDTO actual = model.getReportajePorEvento(eventoSeleccionado.getId_evento(), nombreReportero);
        if (actual == null) {
            SwingUtil.showMessage("No se encontró reportaje para el evento seleccionado.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
        }
        return actual;
    }

    private void solicitarRevisionReportaje() {
        if (isModoPendientes()) {
            SwingUtil.showMessage("Debes seleccionar un evento entregado para solicitar revisión.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        EntregarReportajeDTO eventoSeleccionado = (EntregarReportajeDTO) view.getComboEventos().getSelectedItem();
        if (eventoSeleccionado == null) {
            SwingUtil.showMessage("Debes seleccionar un evento entregado.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        EntregarReportajeDTO actual = model.getReportajePorEvento(eventoSeleccionado.getId_evento(), nombreReportero);
        if (actual == null) {
            SwingUtil.showMessage("No se encontró reportaje para el evento seleccionado.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!actual.puedeSolicitarRevision()) {
            SwingUtil.showMessage("Solo el reportero que entregó el reportaje puede solicitar revisión una única vez.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        model.solicitarRevision(actual.getId_reportaje());
        SwingUtil.showMessage("Revisión solicitada correctamente. El contenido queda bloqueado para edición.", "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
        recargarEventosSegunFiltro();
    }

    private boolean isReportajeBloqueadoPorRevision(EntregarReportajeDTO reportaje) {
        return reportaje != null && !isModoPendientes() && reportaje.isBloqueadoPorRevision();
    }

    private void restaurarVersionSeleccionada() {
        EntregarReportajeDTO eventoSeleccionado = (EntregarReportajeDTO) view.getComboEventos().getSelectedItem();
        if (eventoSeleccionado == null || isModoPendientes()) {
            SwingUtil.showMessage("Debes seleccionar un evento entregado.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        EntregarReportajeDTO actual = model.getReportajePorEvento(eventoSeleccionado.getId_evento(), nombreReportero);
        if (actual == null || !actual.isEditablePorReportero()) {
            SwingUtil.showMessage("No tienes permisos para restaurar este reportaje.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (versionesCargadas == null || versionesCargadas.isEmpty()) {
            SwingUtil.showMessage("No hay versiones para restaurar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idx = view.getIndiceVersionSeleccionada();
        if (idx < 0 || idx >= versionesCargadas.size()) {
            SwingUtil.showMessage("Debes seleccionar una versión.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        EntregarReportajeDTO version = versionesCargadas.get(idx);
        String nuevoSubtitulo = version.getSubtitulo_guardado() == null ? actual.getSubtitulo() : version.getSubtitulo_guardado();
        String nuevoCuerpo = version.getCuerpo_guardado() == null ? actual.getCuerpo() : version.getCuerpo_guardado();

        registrarActualizacion(actual, nuevoSubtitulo, nuevoCuerpo,
                "Versión restaurada y nueva versión registrada correctamente.");
    }

    private void registrarActualizacion(EntregarReportajeDTO actual, String nuevoSubtitulo, String nuevoCuerpo,
            String mensajeExito) {
        boolean cambiaSubtitulo = !nuevoSubtitulo.equals(actual.getSubtitulo());
        boolean cambiaCuerpo = !nuevoCuerpo.equals(actual.getCuerpo());

        if (!cambiaSubtitulo && !cambiaCuerpo) {
            SwingUtil.showMessage("No hay cambios para guardar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        model.actualizarReportaje(actual.getId_reportaje(), nuevoSubtitulo, nuevoCuerpo);
        model.insertarVersion(actual.getId_reportaje(), cambiaSubtitulo ? nuevoSubtitulo : null,
                cambiaCuerpo ? nuevoCuerpo : null);

        SwingUtil.showMessage(mensajeExito, "Éxito", JOptionPane.INFORMATION_MESSAGE);
        recargarEventosSegunFiltro();
    }
}