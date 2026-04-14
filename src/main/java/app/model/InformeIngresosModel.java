package app.model;

import java.util.List;
import app.dto.InformeIngresosDTO;
import giis.demo.util.Database;

public class InformeIngresosModel {
    // Instanciamos la BD directamente, como en el resto de tus modelos
    private Database db = new Database(); 

    public List<InformeIngresosDTO> obtenerIngresosPorAgencia(String nombreAgencia) {
        String sql = 
            "SELECT " +
            "    t.nombre AS tematica, " +
            "    e.id_evento, " +
            "    e.descripcion AS nombre_evento, " + 
            "    emp.nombre AS nombre_empresa, " +
            "    CASE WHEN aet.id_empresa IS NOT NULL THEN 1 ELSE 0 END AS tiene_tarifa, " +
            "    COALESCE(aet.tarifa_plana, 0.0) AS cuota_mensual, " + // <--- CORREGIDO AQUÍ
            "    e.precio AS precio_evento, " +
            "    o.estado AS estado_acceso " +
            "FROM Evento e " +
            "JOIN Agencia a ON e.id_agencia = a.id_agencia " + 
            "JOIN Evento_Tematica et ON e.id_evento = et.id_evento " +
            "JOIN Tematica t ON et.id_tematica = t.id_tematica " +
            "JOIN Ofrecimiento o ON e.id_evento = o.id_evento " +
            "JOIN Empresa_Comunicacion emp ON o.id_empresa = emp.id_empresa " +
            "LEFT JOIN Agencia_Empresa_Tarifa aet ON emp.id_empresa = aet.id_empresa " +
            "                                     AND aet.id_agencia = e.id_agencia " +
            "WHERE a.nombre = ? " + 
            "  AND o.estado = 'ACEPTADO' " + 
            "ORDER BY t.nombre, e.id_evento"; 

        return db.executeQueryPojo(InformeIngresosDTO.class, sql, nombreAgencia);
    }
}