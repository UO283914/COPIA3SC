package app.model;

import java.util.List;

import app.dto.EscogerEventoFreelanceDTO;
import giis.demo.util.Database;

public class EscogerEventoFreelanceModel {

    private Database db = new Database();

    public boolean esFreelance(String nombreReportero) {
        String sql = "SELECT r.id_reportero "
                + "FROM Reportero r "
                + "WHERE r.nombre = ? AND COALESCE(r.es_freelance, 0) = 1";
        List<EscogerEventoFreelanceDTO> rows = db.executeQueryPojo(EscogerEventoFreelanceDTO.class, sql, nombreReportero);
        return !rows.isEmpty();
    }

    public String getEspecialidadesReportero(String nombreReportero) {
        String sql = "SELECT COALESCE(GROUP_CONCAT(DISTINCT t.nombre), 'Sin especialidades') AS especialidades_reportero "
                + "FROM Reportero r "
                + "LEFT JOIN Reportero_Tematica rt ON rt.id_reportero = r.id_reportero "
                + "LEFT JOIN Tematica t ON t.id_tematica = rt.id_tematica "
                + "WHERE r.nombre = ?";
        List<EscogerEventoFreelanceDTO> rows = db.executeQueryPojo(EscogerEventoFreelanceDTO.class, sql, nombreReportero);
        return rows.isEmpty() ? "Sin especialidades" : rows.get(0).getEspecialidades_reportero();
    }

    public List<EscogerEventoFreelanceDTO> getEventosDisponibles(String nombreReportero) {
        String sql = "SELECT e.id_evento, e.descripcion, e.fecha, a.nombre AS nombre_agencia, "
                + "COALESCE(GROUP_CONCAT(DISTINCT t.nombre), 'Sin tematicas') AS tematicas_evento, "
                + "pf.estado_preferencia "
                + "FROM Reportero r "
                + "JOIN Reportero_Tematica rt ON rt.id_reportero = r.id_reportero "
                + "JOIN Evento_Tematica et ON et.id_tematica = rt.id_tematica "
                + "JOIN Evento e ON e.id_evento = et.id_evento "
                + "JOIN Agencia a ON a.id_agencia = e.id_agencia "
                + "LEFT JOIN Evento_Tematica et_all ON et_all.id_evento = e.id_evento "
                + "LEFT JOIN Tematica t ON t.id_tematica = et_all.id_tematica "
                + "LEFT JOIN Preferencia_Freelance pf ON pf.id_evento = e.id_evento AND pf.id_reportero = r.id_reportero "
                + "WHERE r.nombre = ? "
                + "AND COALESCE(r.es_freelance, 0) = 1 "
                + "AND COALESCE(e.disponible_freelance, 0) = 1 "
                + "GROUP BY e.id_evento, e.descripcion, e.fecha, a.nombre, pf.estado_preferencia "
                + "ORDER BY e.fecha, e.id_evento";
        return db.executeQueryPojo(EscogerEventoFreelanceDTO.class, sql, nombreReportero);
    }

    public void guardarPreferencia(String nombreReportero, int idEvento, String estadoPreferencia) {
        Integer idReportero = getIdReportero(nombreReportero);
        String sql = "INSERT INTO Preferencia_Freelance (id_evento, id_reportero, estado_preferencia, fecha_actualizacion) "
                + "VALUES (?, ?, ?, CURRENT_TIMESTAMP) "
                + "ON CONFLICT(id_evento, id_reportero) DO UPDATE SET "
                + "estado_preferencia = excluded.estado_preferencia, "
                + "fecha_actualizacion = CURRENT_TIMESTAMP";
        db.executeUpdate(sql, idEvento, idReportero, estadoPreferencia.toUpperCase());
    }

    private Integer getIdReportero(String nombreReportero) {
        String sql = "SELECT id_reportero FROM Reportero WHERE nombre = ?";
        List<EscogerEventoFreelanceDTO> rows = db.executeQueryPojo(EscogerEventoFreelanceDTO.class, sql, nombreReportero);
        return rows.isEmpty() ? null : rows.get(0).getId_reportero();
    }
}
