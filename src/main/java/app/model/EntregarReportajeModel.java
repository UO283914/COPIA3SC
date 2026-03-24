package app.model;

import java.util.List;

import app.dto.EntregarReportajeDTO;
import giis.demo.util.Database;

public class EntregarReportajeModel {

    private Database db = new Database();

    public List<EntregarReportajeDTO> getEventosPendientes(String nombreReportero) {
        String sql = "SELECT e.id_evento, e.descripcion "
                + "FROM Evento e "
                + "JOIN Asignacion a ON e.id_evento = a.id_evento "
                + "JOIN Reportero r ON a.id_reportero = r.id_reportero "
                + "LEFT JOIN Reportaje rep ON rep.id_evento = e.id_evento "
                + "WHERE r.nombre = ? AND (rep.id_reportaje IS NULL OR rep.fecha_entrega IS NULL) "
                + "ORDER BY e.fecha";
        return db.executeQueryPojo(EntregarReportajeDTO.class, sql, nombreReportero);
    }

    public List<EntregarReportajeDTO> getEventosEntregados(String nombreReportero) {
        String sql = "SELECT e.id_evento, e.descripcion, rep.id_reportaje, rep.id_reportero AS id_reportero_entrega, "
                + "autor.nombre AS nombre_reportero_entrega, rep.titulo, rep.subtitulo, rep.cuerpo, rep.estado_revision, "
                + "CASE WHEN autor.nombre = ? AND rep.estado_revision = 'ENTREGADO' THEN 1 ELSE 0 END AS editable "
                + "FROM Evento e "
                + "JOIN Asignacion a ON e.id_evento = a.id_evento "
                + "JOIN Reportero r ON a.id_reportero = r.id_reportero "
                + "JOIN Reportaje rep ON rep.id_evento = e.id_evento AND rep.fecha_entrega IS NOT NULL "
                + "LEFT JOIN Reportero autor ON autor.id_reportero = rep.id_reportero "
                + "WHERE r.nombre = ? "
                + "ORDER BY e.fecha";
        return db.executeQueryPojo(EntregarReportajeDTO.class, sql, nombreReportero, nombreReportero);
    }

    public EntregarReportajeDTO getReportajePorEvento(int idEvento, String nombreReportero) {
        String sql = "SELECT e.id_evento, e.descripcion, rep.id_reportaje, rep.id_reportero AS id_reportero_entrega, "
                + "autor.nombre AS nombre_reportero_entrega, rep.titulo, rep.subtitulo, rep.cuerpo, rep.estado_revision, "
                + "CASE WHEN autor.nombre = ? AND rep.estado_revision = 'ENTREGADO' THEN 1 ELSE 0 END AS editable "
                + "FROM Evento e "
                + "JOIN Asignacion a ON e.id_evento = a.id_evento "
                + "JOIN Reportero r ON a.id_reportero = r.id_reportero "
                + "JOIN Reportaje rep ON rep.id_evento = e.id_evento AND rep.fecha_entrega IS NOT NULL "
                + "LEFT JOIN Reportero autor ON autor.id_reportero = rep.id_reportero "
                + "WHERE e.id_evento = ? AND r.nombre = ?";
        List<EntregarReportajeDTO> rows = db.executeQueryPojo(EntregarReportajeDTO.class, sql, nombreReportero, idEvento,
                nombreReportero);
        return rows.isEmpty() ? null : rows.get(0);
    }

    public EntregarReportajeDTO getBorradorPorEvento(int idEvento, String nombreReportero) {
        String sql = "SELECT e.id_evento, e.descripcion, rep.id_reportaje, rep.id_reportero AS id_reportero_entrega, "
                + "autor.nombre AS nombre_reportero_entrega, rep.titulo, rep.subtitulo, rep.cuerpo, rep.estado_revision, 1 AS editable "
                + "FROM Evento e "
                + "JOIN Asignacion a ON e.id_evento = a.id_evento "
                + "JOIN Reportero r ON a.id_reportero = r.id_reportero "
                + "LEFT JOIN Reportaje rep ON rep.id_evento = e.id_evento AND rep.fecha_entrega IS NULL "
                + "LEFT JOIN Reportero autor ON autor.id_reportero = rep.id_reportero "
                + "WHERE e.id_evento = ? AND r.nombre = ?";
        List<EntregarReportajeDTO> rows = db.executeQueryPojo(EntregarReportajeDTO.class, sql, idEvento, nombreReportero);
        return rows.isEmpty() ? null : rows.get(0);
    }

    public List<EntregarReportajeDTO> getVersionesReportaje(int idReportaje) {
        String sql = "SELECT id_version, fecha_cambio, hora_cambio, subtitulo_guardado, cuerpo_guardado "
                + "FROM Version_Reportaje WHERE id_reportaje = ? ORDER BY fecha_cambio DESC, hora_cambio DESC, id_version DESC";
        return db.executeQueryPojo(EntregarReportajeDTO.class, sql, idReportaje);
    }

    public List<EntregarReportajeDTO> getMultimedia(int idReportaje, String tipo, String estado, String nombreReportero) {
        String sql = "SELECT m.id_multimedia, m.id_reportaje, m.id_reportero AS id_reportero_multimedia, "
                + "r.nombre AS nombre_reportero_multimedia, m.ruta, m.tipo, m.estado, "
                + "CASE WHEN r.nombre = ? THEN 1 ELSE 0 END AS editable_multimedia "
                + "FROM Multimedia m "
                + "JOIN Reportero r ON r.id_reportero = m.id_reportero "
                + "WHERE m.id_reportaje = ? AND m.tipo = ? AND m.estado = ? "
                + "ORDER BY m.id_multimedia DESC";
        return db.executeQueryPojo(EntregarReportajeDTO.class, sql, nombreReportero, idReportaje, tipo, estado);
    }

    public boolean existeTitulo(String titulo) {
        return existeTituloEnOtroReportaje(titulo, null);
    }

    public boolean existeTituloEnOtroReportaje(String titulo, Integer idReportajeExcluir) {
        String sql = "SELECT id_reportaje FROM Reportaje WHERE UPPER(titulo) = UPPER(?)";
        List<EntregarReportajeDTO> rows = db.executeQueryPojo(EntregarReportajeDTO.class, sql, titulo);
        for (EntregarReportajeDTO row : rows) {
            if (idReportajeExcluir == null || !idReportajeExcluir.equals(row.getId_reportaje())) {
                return true;
            }
        }
        return false;
    }

    public boolean existeRutaMultimedia(String ruta) {
        String sql = "SELECT id_multimedia FROM Multimedia WHERE UPPER(ruta) = UPPER(?)";
        List<EntregarReportajeDTO> rows = db.executeQueryPojo(EntregarReportajeDTO.class, sql, ruta);
        return !rows.isEmpty();
    }

    public void insertarReportaje(int idEvento, String nombreReportero, String titulo, String subtitulo, String cuerpo) {
        String sql = "INSERT INTO Reportaje (id_evento, id_reportero, titulo, subtitulo, cuerpo, fecha_entrega) "
                + "VALUES (?, (SELECT id_reportero FROM Reportero WHERE nombre = ?), ?, ?, ?, datetime('now'))";
        db.executeUpdate(sql, idEvento, nombreReportero, titulo, subtitulo, cuerpo);
    }

    public void crearBorradorSiNoExiste(int idEvento) {
        String sql = "INSERT INTO Reportaje (id_evento) "
                + "SELECT ? WHERE NOT EXISTS (SELECT 1 FROM Reportaje WHERE id_evento = ?)";
        db.executeUpdate(sql, idEvento, idEvento);
    }

    public void entregarBorrador(int idReportaje, String nombreReportero, String titulo, String subtitulo, String cuerpo) {
        String sql = "UPDATE Reportaje SET id_reportero = (SELECT id_reportero FROM Reportero WHERE nombre = ?), "
                + "titulo = ?, subtitulo = ?, cuerpo = ?, fecha_entrega = datetime('now') WHERE id_reportaje = ?";
        db.executeUpdate(sql, nombreReportero, titulo, subtitulo, cuerpo, idReportaje);
    }

    public void insertarMultimedia(int idReportaje, String nombreReportero, String tipo, String ruta) {
        String sql = "INSERT INTO Multimedia (id_reportaje, id_reportero, tipo, ruta, estado) "
                + "VALUES (?, (SELECT id_reportero FROM Reportero WHERE nombre = ?), ?, ?, 'BORRADOR')";
        db.executeUpdate(sql, idReportaje, nombreReportero, tipo, ruta);
    }

    public void actualizarReportaje(int idReportaje, String subtitulo, String cuerpo) {
        String sql = "UPDATE Reportaje SET subtitulo = ?, cuerpo = ? WHERE id_reportaje = ?";
        db.executeUpdate(sql, subtitulo, cuerpo, idReportaje);
    }

    public void insertarVersion(int idReportaje, String subtituloGuardado, String cuerpoGuardado) {
        String sql = "INSERT INTO Version_Reportaje (id_reportaje, fecha_cambio, hora_cambio, subtitulo_guardado, cuerpo_guardado) "
                + "VALUES (?, date('now'), time('now'), ?, ?)";
        db.executeUpdate(sql, idReportaje, subtituloGuardado, cuerpoGuardado);
    }

    public void solicitarRevision(int idReportaje) {
        String sql = "UPDATE Reportaje SET estado_revision = 'EN_REVISION' WHERE id_reportaje = ?";
        db.executeUpdate(sql, idReportaje);
    }

    public void actualizarEstadoMultimedia(int idMultimedia, String nuevoEstado) {
        String sql = "UPDATE Multimedia SET estado = ? WHERE id_multimedia = ?";
        db.executeUpdate(sql, nuevoEstado, idMultimedia);
    }

    public void eliminarMultimedia(int idMultimedia) {
        String sql = "DELETE FROM Multimedia WHERE id_multimedia = ?";
        db.executeUpdate(sql, idMultimedia);
    }
}