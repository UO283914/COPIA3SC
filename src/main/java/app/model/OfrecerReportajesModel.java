package app.model;

import java.util.List;

import app.dto.OfrecerReportajesDTO;
import giis.demo.util.Database;

public class OfrecerReportajesModel {

    private Database db = new Database();

    /**
     * Obtiene los eventos de la agencia cuya asignacion de reporteros ya ha finalizado,
     * junto con sus tematicas y el estado de embargo del reportaje.
     */
    public List<OfrecerReportajesDTO> getEventosConReportero(String nombreAgencia) {
        String sql = "SELECT e.id_evento, e.descripcion AS nombre_evento, "
                + "COALESCE(GROUP_CONCAT(DISTINCT r.nombre), 'Sin reportero') AS reportero_asignado, "
                + "COALESCE(GROUP_CONCAT(DISTINCT t.nombre), 'Sin tematica') AS tematicas_evento, "
                + "rep.fecha_fin_embargo "
                + "FROM Evento e "
                + "LEFT JOIN Reportaje rep ON e.id_evento = rep.id_evento "
                + "JOIN Asignacion a ON e.id_evento = a.id_evento "
                + "JOIN Reportero r ON a.id_reportero = r.id_reportero "
                + "JOIN Agencia ag ON e.id_agencia = ag.id_agencia "
                + "LEFT JOIN Evento_Tematica et ON et.id_evento = e.id_evento "
                + "LEFT JOIN Tematica t ON t.id_tematica = et.id_tematica "
                + "WHERE ag.nombre = ? "
                + "GROUP BY e.id_evento, e.descripcion, rep.fecha_fin_embargo, e.finalizado "
                + "HAVING COUNT(a.id_reportero) > 0 "
                + "AND COALESCE(e.finalizado, 0) = 1";

        return db.executeQueryPojo(OfrecerReportajesDTO.class, sql, nombreAgencia);
    }

    /**
     * Empresas candidatas sin oferta.
     */
    public List<OfrecerReportajesDTO> getEmpresasSinOferta(int idEvento, String nombreAgencia,
            boolean soloCoincidentes, boolean soloTarifaPlana) {
        String sql = "SELECT DISTINCT ec.id_empresa, ec.nombre AS nombre_empresa, ec.acepta_embargos, "
                + "COALESCE(aet.tarifa_plana, 0) AS tarifa_plana, "
                + "CASE WHEN COALESCE(aet.tarifa_plana, 0) > 0 THEN 1 ELSE 0 END AS tiene_tarifa_plana, "
                + "CASE WHEN aet.id_empresa IS NULL THEN 1 ELSE COALESCE(aet.al_corriente_pago, 0) END AS al_corriente_pago, "
                + "CASE WHEN COALESCE(aet.tarifa_plana, 0) > 0 THEN 'SI' ELSE 'NO' END AS tarifa_plana_info, "
                + "CASE WHEN aet.id_empresa IS NULL OR COALESCE(aet.al_corriente_pago, 0) = 1 THEN 'SI' ELSE 'NO' END AS pago_info "
                + "FROM Empresa_Comunicacion ec ";

        sql += "LEFT JOIN Agencia ag ON ag.nombre = ? "
                + "LEFT JOIN Agencia_Empresa_Tarifa aet ON aet.id_empresa = ec.id_empresa AND aet.id_agencia = ag.id_agencia ";

        if (soloCoincidentes) {
            sql += "JOIN Empresa_Tematica ect ON ec.id_empresa = ect.id_empresa "
                    + "JOIN Evento_Tematica et ON et.id_tematica = ect.id_tematica AND et.id_evento = ? ";
        }

        sql += "WHERE ec.id_empresa NOT IN ("
                + "  SELECT id_empresa FROM Ofrecimiento WHERE id_evento = ?"
                + ") ";

        if (soloTarifaPlana) {
            sql += "AND COALESCE(aet.tarifa_plana, 0) > 0 ";
        }

        sql += "ORDER BY ec.nombre";

        if (soloCoincidentes) {
            return db.executeQueryPojo(OfrecerReportajesDTO.class, sql, nombreAgencia, idEvento, idEvento);
        }
        return db.executeQueryPojo(OfrecerReportajesDTO.class, sql, nombreAgencia, idEvento);
    }

    /**
     * Empresas a las que ya se ha ofrecido el reportaje.
     */
    public List<OfrecerReportajesDTO> getEmpresasConOferta(int idEvento, String nombreAgencia) {
        String sql = "SELECT e.id_empresa, e.nombre AS nombre_empresa, o.estado, o.tiene_acceso, e.acepta_embargos, "
                + "COALESCE(aet.tarifa_plana, 0) AS tarifa_plana, "
                + "CASE WHEN COALESCE(aet.tarifa_plana, 0) > 0 THEN 1 ELSE 0 END AS tiene_tarifa_plana, "
                + "CASE WHEN COALESCE(aet.tarifa_plana, 0) > 0 THEN 'SI' ELSE 'NO' END AS tarifa_plana_info, "
                + "CASE WHEN aet.id_empresa IS NULL OR COALESCE(aet.al_corriente_pago, 0) = 1 THEN 'SI' ELSE 'NO' END AS pago_info, "
                + "CASE WHEN aet.id_empresa IS NULL THEN 1 ELSE COALESCE(aet.al_corriente_pago, 0) END AS al_corriente_pago "
                + "FROM Empresa_Comunicacion e "
                + "JOIN Ofrecimiento o ON e.id_empresa = o.id_empresa "
                + "LEFT JOIN Agencia ag ON ag.nombre = ? "
                + "LEFT JOIN Agencia_Empresa_Tarifa aet ON aet.id_empresa = e.id_empresa AND aet.id_agencia = ag.id_agencia "
                + "WHERE o.id_evento = ? "
                + "ORDER BY e.nombre";
        return db.executeQueryPojo(OfrecerReportajesDTO.class, sql, nombreAgencia, idEvento);
    }

    public OfrecerReportajesDTO getDetalleOfrecimiento(int idEvento, int idEmpresa) {
        String sql = "SELECT o.id_evento, o.id_empresa, o.estado, o.tiene_acceso, e.email "
                + "FROM Ofrecimiento o "
                + "JOIN Empresa_Comunicacion e ON o.id_empresa = e.id_empresa "
                + "WHERE o.id_evento = ? AND o.id_empresa = ?";
        List<OfrecerReportajesDTO> res = db.executeQueryPojo(OfrecerReportajesDTO.class, sql, idEvento, idEmpresa);
        return res.isEmpty() ? null : res.get(0);
    }

    public void insertarOfrecimientos(int idEvento, int idEmpresa) {
        String sql = "INSERT INTO Ofrecimiento (id_evento, id_empresa, estado, tiene_acceso) "
                + "VALUES (?, ?, 'PENDIENTE', 0)";
        db.executeUpdate(sql, idEvento, idEmpresa);
    }

    public void eliminarOfrecimiento(int idEvento, int idEmpresa) {
        String sql = "DELETE FROM Ofrecimiento WHERE id_evento = ? AND id_empresa = ?";
        db.executeUpdate(sql, idEvento, idEmpresa);
    }
}
