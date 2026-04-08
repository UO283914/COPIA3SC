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

    // MÉTODO UNIFICADO ACTUALIZADO
    public List<gestionarOfrecimientosReportajesDTO> getOfrecimientosFiltrados(
            String nombreEmpresa, String estadoFiltro, String tematicaFiltro, 
            Double precioMin, Double precioMax, boolean soloEmbargosActivos) { // Añadido boolean
        
        List<Object> parametros = new ArrayList<>();
        
        // Añadimos las dos columnas nuevas y el JOIN
        String sql = "SELECT e.id_evento AS idEvento, " +
                     "e.descripcion AS descripcionEvento, " +
                     "e.fecha AS fechaEvento, " +
                     "a.nombre AS nombreAgencia, " +
                     "e.precio AS precio, " + 
                     "o.estado AS estado, " +
                     "o.tiene_acceso AS tieneAcceso, " +
                     "o.acceso_especial_embargo AS accesoEspecialEmbargo, " + // NUEVO
                     "rep.fecha_fin_embargo AS fechaFinEmbargo " + // NUEVO
                     "FROM Ofrecimiento o " +
                     "JOIN Evento e ON o.id_evento = e.id_evento " +
                     "JOIN Agencia a ON e.id_agencia = a.id_agencia " +
                     "JOIN Empresa_Comunicacion emp ON o.id_empresa = emp.id_empresa " +
                     "LEFT JOIN Reportaje rep ON e.id_evento = rep.id_evento " + // NUEVO JOIN
                     "WHERE emp.nombre = ? ";
        
        parametros.add(nombreEmpresa);

        if (estadoFiltro.equals("PENDIENTE")) {
            sql += " AND o.estado = 'PENDIENTE'";
        } else {
            sql += " AND o.estado IN ('ACEPTADO', 'RECHAZADO')";
        }

        if (tematicaFiltro != null && !tematicaFiltro.equals("Todas las temáticas")) {
            sql += " AND e.id_evento IN (SELECT id_evento FROM Evento_Tematica evt " +
                   " JOIN Tematica t ON evt.id_tematica = t.id_tematica " +
                   " WHERE t.nombre = ?) ";
            parametros.add(tematicaFiltro);
        }
        
        if (precioMin != null) {
            sql += " AND e.precio >= ?";
            parametros.add(precioMin);
        }
        if (precioMax != null) {
            sql += " AND e.precio <= ?";
            parametros.add(precioMax);
        }

        // 4. NUEVO FILTRO HU 34351 (Embargos activos)
        if (soloEmbargosActivos) {
            sql += " AND rep.fecha_fin_embargo IS NOT NULL AND rep.fecha_fin_embargo > date('now') ";
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