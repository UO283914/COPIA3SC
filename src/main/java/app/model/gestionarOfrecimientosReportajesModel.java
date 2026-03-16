package app.model;

import java.util.ArrayList;
import java.util.List;
import app.dto.gestionarOfrecimientosReportajesDTO;
import giis.demo.util.Database;

public class gestionarOfrecimientosReportajesModel {
    private Database db = new Database();

    public List<Object[]> obtenerTodasLasTematicas() {
        String sql = "SELECT nombre FROM Tematica ORDER BY nombre"; 
        return db.executeQueryArray(sql); 
    }

    public List<Object[]> obtenerTematicasEmpresa(String nombreEmpresa) {
        String sql = "SELECT t.nombre FROM Tematica t " +
                     "JOIN Empresa_Tematica et ON t.id_tematica = et.id_tematica " +
                     "JOIN Empresa_Comunicacion emp ON et.id_empresa = emp.id_empresa " +
                     "WHERE emp.nombre = ? ORDER BY t.nombre";
        return db.executeQueryArray(sql, nombreEmpresa);
    }

    // MÉTODO UNIFICADO: Recibe todos los filtros y construye el SQL dinámicamente
    public List<gestionarOfrecimientosReportajesDTO> getOfrecimientosFiltrados(
            String nombreEmpresa, String estadoFiltro, String tematicaFiltro, Double precioMin, Double precioMax) {
        
        List<Object> parametros = new ArrayList<>();
        
        String sql = "SELECT e.id_evento AS idEvento, " +
                     "e.descripcion AS descripcionEvento, " +
                     "e.fecha AS fechaEvento, " +
                     "a.nombre AS nombreAgencia, " +
                     "e.precio AS precio, " + // Ahora traemos el precio
                     "o.estado AS estado, " +
                     "o.tiene_acceso AS tieneAcceso " + 
                     "FROM Ofrecimiento o " +
                     "JOIN Evento e ON o.id_evento = e.id_evento " +
                     "JOIN Agencia a ON e.id_agencia = a.id_agencia " +
                     "JOIN Empresa_Comunicacion emp ON o.id_empresa = emp.id_empresa " +
                     "WHERE emp.nombre = ? ";
        
        parametros.add(nombreEmpresa);

        // 1. Filtro de Estado
        if (estadoFiltro.equals("PENDIENTE")) {
            sql += " AND o.estado = 'PENDIENTE'";
        } else {
            sql += " AND o.estado IN ('ACEPTADO', 'RECHAZADO')";
        }

        // 2. Filtro de Temática
        if (tematicaFiltro != null && !tematicaFiltro.equals("Todas las temáticas")) {
            sql += " AND e.id_evento IN (SELECT id_evento FROM Evento_Tematica evt " +
                   " JOIN Tematica t ON evt.id_tematica = t.id_tematica " +
                   " WHERE t.nombre = ?) ";
            parametros.add(tematicaFiltro);
        }
        
        // 3. Filtro de Precios (HU 34085)
        if (precioMin != null) {
            sql += " AND e.precio >= ?";
            parametros.add(precioMin);
        }
        if (precioMax != null) {
            sql += " AND e.precio <= ?";
            parametros.add(precioMax);
        }
                     
        return db.executeQueryPojo(gestionarOfrecimientosReportajesDTO.class, sql, parametros.toArray());
    }

    public void actualizarEstadoOfrecimiento(String idEvento, String nombreEmpresa, String nuevoEstado) {
        String sql = "UPDATE Ofrecimiento " +
                     "SET estado = ? " +
                     "WHERE id_evento = ? AND id_empresa = (SELECT id_empresa FROM Empresa_Comunicacion WHERE nombre = ?)";
        db.executeUpdate(sql, nuevoEstado, idEvento, nombreEmpresa);
    }
}