package app.dto;

public class gestionarOfrecimientosReportajesDTO {
    private String idEvento;
    private String descripcionEvento;
    private String fechaEvento;
    private String nombreAgencia;
    private String estado;
    private Integer tieneAcceso;
    private String accesoVisible;
    
    // NUEVO CAMPO PARA LA HU 34085
    private Double precio; 

    public String getIdEvento() { return idEvento; }
    public void setIdEvento(String idEvento) { this.idEvento = idEvento; }

    public String getDescripcionEvento() { return descripcionEvento; }
    public void setDescripcionEvento(String descripcionEvento) { this.descripcionEvento = descripcionEvento; }

    public String getFechaEvento() { return fechaEvento; }
    public void setFechaEvento(String fechaEvento) { this.fechaEvento = fechaEvento; }

    public String getNombreAgencia() { return nombreAgencia; }
    public void setNombreAgencia(String nombreAgencia) { this.nombreAgencia = nombreAgencia; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Integer getTieneAcceso() { return tieneAcceso; }
    public void setTieneAcceso(Integer tieneAcceso) { 
        this.tieneAcceso = tieneAcceso;
        this.accesoVisible = (tieneAcceso != null && tieneAcceso == 1) ? "SÍ" : "NO";
    }

    public String getAccesoVisible() { return accesoVisible; }
    public void setAccesoVisible(String accesoVisible) { this.accesoVisible = accesoVisible; }

    // GETTER Y SETTER DEL PRECIO
    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }
}