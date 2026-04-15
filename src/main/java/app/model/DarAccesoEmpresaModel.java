package app.model;

import giis.demo.util.Database;
import app.dto.EventoDisplayDTO;
import app.dto.EmpresaDisplayDTO;
import java.util.List;

public class DarAccesoEmpresaModel {

	private Database db = new Database();

	// 1. Eventos de la agencia con reportaje, mostrando estado finalizado real desde Evento
	public List<EventoDisplayDTO> getEventosConReportaje(String nombreAgencia) {
		String sql = "SELECT e.id_evento, e.descripcion, e.fecha, "
				+ "CASE WHEN COALESCE(e.finalizado, 0) = 1 THEN 'SÍ' ELSE 'NO' END AS finalizado, "
				+ "COALESCE(GROUP_CONCAT(DISTINCT t.nombre), 'Sin temática') AS tematicas "
				+ "FROM Evento e "
				+ "JOIN Agencia a ON e.id_agencia = a.id_agencia "
				+ "JOIN Reportaje r ON e.id_evento = r.id_evento "
				+ "LEFT JOIN Evento_Tematica et ON e.id_evento = et.id_evento "
				+ "LEFT JOIN Tematica t ON et.id_tematica = t.id_tematica "
				+ "WHERE a.nombre = ? "
				+ "GROUP BY e.id_evento, e.descripcion, e.fecha, e.finalizado";
		return db.executeQueryPojo(EventoDisplayDTO.class, sql, nombreAgencia);
	}

	public List<EmpresaDisplayDTO> getEmpresasAceptadasByAcceso(Integer idEvento, boolean conAcceso) {
		String sql = "SELECT emp.id_empresa, emp.nombre, o.tiene_acceso AS tieneAcceso, o.descargado, "
				+ "CASE WHEN aet.id_empresa IS NULL THEN 0 ELSE 1 END AS tieneTarifa, "
				+ "COALESCE(aet.al_corriente_pago, 0) AS alCorrientePago, "
				+ "COALESCE(o.reportaje_pagado, 0) AS reportajePagado, "
				+ "CASE WHEN (aet.id_empresa IS NOT NULL AND aet.al_corriente_pago = 1) "
				+ "OR (aet.id_empresa IS NULL AND o.reportaje_pagado = 1) THEN 1 ELSE 0 END AS elegiblePago "
				+ "FROM Empresa_Comunicacion emp "
				+ "JOIN Ofrecimiento o ON emp.id_empresa = o.id_empresa "
				+ "JOIN Evento ev ON ev.id_evento = o.id_evento "
				+ "LEFT JOIN Agencia_Empresa_Tarifa aet "
				+ "ON aet.id_agencia = ev.id_agencia AND aet.id_empresa = emp.id_empresa "
				+ "WHERE o.id_evento = ? "
				+ "AND o.estado = 'ACEPTADO' "
				+ "AND o.tiene_acceso = ? "
				+ "AND COALESCE(ev.finalizado, 0) = 1";
		return db.executeQueryPojo(EmpresaDisplayDTO.class, sql, idEvento, conAcceso ? 1 : 0);
	}

	public void concederAcceso(Integer idEvento, Integer idEmpresa) {
		if (idEvento == null || idEmpresa == null) {
			throw new giis.demo.util.ApplicationException("Error interno: Los IDs están vacíos.");
		}
		if (!isEventoDistribuible(idEvento)) {
			throw new giis.demo.util.ApplicationException(
					"No se puede conceder acceso: el evento no está finalizado.");
		}
		if (!isEmpresaElegibleParaAcceso(idEvento, idEmpresa)) {
			throw new giis.demo.util.ApplicationException(
					"No se puede conceder acceso: la empresa no cumple estado de pago (tarifa al corriente o reportaje pagado).");
		}
		String sql = "UPDATE Ofrecimiento SET tiene_acceso = 1 WHERE id_evento = ? AND id_empresa = ?";
		db.executeUpdate(sql, idEvento, idEmpresa);
	}

	public void revocarAcceso(Integer idEvento, Integer idEmpresa) {
		if (idEvento == null || idEmpresa == null) {
			throw new giis.demo.util.ApplicationException("Error interno: Los IDs están vacíos.");
		}
		String sql = "UPDATE Ofrecimiento SET tiene_acceso = 0 WHERE id_evento = ? AND id_empresa = ? AND descargado = 0";
		db.executeUpdate(sql, idEvento, idEmpresa);
	}

	private boolean isEventoDistribuible(Integer idEvento) {
		String sql = "SELECT COALESCE(finalizado, 0) FROM Evento WHERE id_evento = ?";
		List<Object[]> rows = db.executeQueryArray(sql, idEvento);
		return rows != null && !rows.isEmpty() && ((Number) rows.get(0)[0]).intValue() == 1;
	}

	private boolean isEmpresaElegibleParaAcceso(Integer idEvento, Integer idEmpresa) {
		String sql = "SELECT COUNT(*) "
				+ "FROM Ofrecimiento o "
				+ "JOIN Evento ev ON ev.id_evento = o.id_evento "
				+ "LEFT JOIN Agencia_Empresa_Tarifa aet "
				+ "ON aet.id_agencia = ev.id_agencia AND aet.id_empresa = o.id_empresa "
				+ "WHERE o.id_evento = ? "
				+ "AND o.id_empresa = ? "
				+ "AND o.estado = 'ACEPTADO' "
				+ "AND ( (aet.id_empresa IS NOT NULL AND aet.al_corriente_pago = 1) "
				+ "   OR (aet.id_empresa IS NULL AND o.reportaje_pagado = 1) )";
		List<Object[]> rows = db.executeQueryArray(sql, idEvento, idEmpresa);
		return rows != null && !rows.isEmpty() && ((Number) rows.get(0)[0]).intValue() > 0;
	}
}
