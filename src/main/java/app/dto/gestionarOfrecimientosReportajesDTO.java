package app.dto;

public class gestionarOfrecimientosReportajesDTO {
    private String idEvento;
    private String descripcionEvento;
    private String fechaEvento;
    private String nombreAgencia;
    private String estado;
    private Integer tieneAcceso;
    private String accesoVisible;
    
    // CAMPO PARA LA HU 34085
    private Double precio; 
    
    // --- NUEVOS CAMPOS PARA LA HU 34351 (Embargos) ---
    private String fechaFinEmbargo;
    private Integer accesoEspecialEmbargo;

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
        this.accesoVisible = (tieneAcceso != null && tieneAcceso == 1) ? "Sí (Total)" : "No";
    }

    public String getAccesoVisible() { return accesoVisible; }
    public void setAccesoVisible(String accesoVisible) { this.accesoVisible = accesoVisible; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }

    // --- GETTERS Y SETTERS HU 34351 ---
    public String getFechaFinEmbargo() { return fechaFinEmbargo; }
    public void setFechaFinEmbargo(String fechaFinEmbargo) { this.fechaFinEmbargo = fechaFinEmbargo; }

    public Integer getAccesoEspecialEmbargo() { return accesoEspecialEmbargo; }
    public void setAccesoEspecialEmbargo(Integer accesoEspecialEmbargo) { this.accesoEspecialEmbargo = accesoEspecialEmbargo; }

    // Métodos mágicos para que la tabla no muestre cosas vacías
    public String getFechaFinEmbargoPantalla() {
        if (this.fechaFinEmbargo == null || this.fechaFinEmbargo.trim().isEmpty()) {
            return "-"; 
        }
        return this.fechaFinEmbargo;
    }

    public String getAccesoEspecialPantalla() {
        return (accesoEspecialEmbargo != null && accesoEspecialEmbargo == 1) ? "Sí (Parcial)" : "No";
    }
}