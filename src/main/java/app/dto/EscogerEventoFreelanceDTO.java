package app.dto;

public class EscogerEventoFreelanceDTO {

    private int id_reportero;
    private int id_evento;
    private String descripcion;
    private String fecha;
    private String nombre_agencia;
    private String tematicas_evento;
    private String estado_preferencia;
    private String especialidades_reportero;

    public int getId_reportero() { return id_reportero; }
    public void setId_reportero(int id_reportero) { this.id_reportero = id_reportero; }

    public int getId_evento() { return id_evento; }
    public void setId_evento(int id_evento) { this.id_evento = id_evento; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getNombre_agencia() { return nombre_agencia; }
    public void setNombre_agencia(String nombre_agencia) { this.nombre_agencia = nombre_agencia; }

    public String getTematicas_evento() { return tematicas_evento; }
    public void setTematicas_evento(String tematicas_evento) { this.tematicas_evento = tematicas_evento; }

    public String getEstado_preferencia() { return estado_preferencia; }
    public void setEstado_preferencia(String estado_preferencia) { this.estado_preferencia = estado_preferencia; }

    public String getEspecialidades_reportero() { return especialidades_reportero; }
    public void setEspecialidades_reportero(String especialidades_reportero) { this.especialidades_reportero = especialidades_reportero; }
}
