package app.dto;

public class InformeIngresosDTO {
    private String tematica;
    private int id_evento;
    private String nombre_evento;
    private String nombre_empresa;
    private int tiene_tarifa;      
    private double cuota_mensual;  
    private double precio_evento;  
    private String estado_acceso;  

    public InformeIngresosDTO() {}

    public String getTematica() { return tematica; }
    public void setTematica(String tematica) { this.tematica = tematica; }

    public int getId_evento() { return id_evento; }
    public void setId_evento(int id_evento) { this.id_evento = id_evento; }

    public String getNombre_evento() { return nombre_evento; }
    public void setNombre_evento(String nombre_evento) { this.nombre_evento = nombre_evento; }

    public String getNombre_empresa() { return nombre_empresa; }
    public void setNombre_empresa(String nombre_empresa) { this.nombre_empresa = nombre_empresa; }

    public int getTiene_tarifa() { return tiene_tarifa; }
    public void setTiene_tarifa(int tiene_tarifa) { this.tiene_tarifa = tiene_tarifa; }

    public double getCuota_mensual() { return cuota_mensual; }
    public void setCuota_mensual(double cuota_mensual) { this.cuota_mensual = cuota_mensual; }

    public double getPrecio_evento() { return precio_evento; }
    public void setPrecio_evento(double precio_evento) { this.precio_evento = precio_evento; }

    public String getEstado_acceso() { return estado_acceso; }
    public void setEstado_acceso(String estado_acceso) { this.estado_acceso = estado_acceso; }
    
    public String getAccesoPantalla() {
        return "ACEPTADO".equalsIgnoreCase(estado_acceso) ? "SÍ" : "NO";
    }
}