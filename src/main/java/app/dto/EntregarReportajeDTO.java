package app.dto;

public class EntregarReportajeDTO {

    private int id_evento;
    private String descripcion;
    private Integer id_reportaje;
    private Integer id_reportero_entrega;
    private String nombre_reportero_entrega;
    private String titulo;
    private String subtitulo;
    private String cuerpo;
    private Integer editable;
    private String estado_revision;

    private Integer id_version;
    private String fecha_cambio;
    private String hora_cambio;
    private String subtitulo_guardado;
    private String cuerpo_guardado;

    private Integer id_multimedia;
    private Integer id_reportero_multimedia;
    private String nombre_reportero_multimedia;
    private String ruta;
    private String tipo;
    private String estado;
    private Integer editable_multimedia;

    public EntregarReportajeDTO() {
        // Constructor vacio para mapeo de dbutils
    }

    public int getId_evento() {
        return id_evento;
    }

    public void setId_evento(int id_evento) {
        this.id_evento = id_evento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getId_reportaje() {
        return id_reportaje;
    }

    public void setId_reportaje(Integer id_reportaje) {
        this.id_reportaje = id_reportaje;
    }

    public Integer getId_reportero_entrega() {
        return id_reportero_entrega;
    }

    public void setId_reportero_entrega(Integer id_reportero_entrega) {
        this.id_reportero_entrega = id_reportero_entrega;
    }

    public String getNombre_reportero_entrega() {
        return nombre_reportero_entrega;
    }

    public void setNombre_reportero_entrega(String nombre_reportero_entrega) {
        this.nombre_reportero_entrega = nombre_reportero_entrega;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public Integer getEditable() {
        return editable;
    }

    public void setEditable(Integer editable) {
        this.editable = editable;
    }


    public String getEstado_revision() {
        return estado_revision;
    }

    public void setEstado_revision(String estado_revision) {
        this.estado_revision = estado_revision;
    }

    public Integer getId_version() {
        return id_version;
    }

    public void setId_version(Integer id_version) {
        this.id_version = id_version;
    }

    public String getFecha_cambio() {
        return fecha_cambio;
    }

    public void setFecha_cambio(String fecha_cambio) {
        this.fecha_cambio = fecha_cambio;
    }

    public String getHora_cambio() {
        return hora_cambio;
    }

    public void setHora_cambio(String hora_cambio) {
        this.hora_cambio = hora_cambio;
    }

    public String getSubtitulo_guardado() {
        return subtitulo_guardado;
    }

    public void setSubtitulo_guardado(String subtitulo_guardado) {
        this.subtitulo_guardado = subtitulo_guardado;
    }

    public String getCuerpo_guardado() {
        return cuerpo_guardado;
    }

    public void setCuerpo_guardado(String cuerpo_guardado) {
        this.cuerpo_guardado = cuerpo_guardado;
    }

    public Integer getId_multimedia() {
        return id_multimedia;
    }

    public void setId_multimedia(Integer id_multimedia) {
        this.id_multimedia = id_multimedia;
    }

    public Integer getId_reportero_multimedia() {
        return id_reportero_multimedia;
    }

    public void setId_reportero_multimedia(Integer id_reportero_multimedia) {
        this.id_reportero_multimedia = id_reportero_multimedia;
    }

    public String getNombre_reportero_multimedia() {
        return nombre_reportero_multimedia;
    }

    public void setNombre_reportero_multimedia(String nombre_reportero_multimedia) {
        this.nombre_reportero_multimedia = nombre_reportero_multimedia;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getEditable_multimedia() {
        return editable_multimedia;
    }

    public void setEditable_multimedia(Integer editable_multimedia) {
        this.editable_multimedia = editable_multimedia;
    }

    public boolean isEditablePorReportero() {
        return editable != null && editable == 1;
    }


    public boolean isEstadoEntregadoParaEditar() {
        return estado_revision == null || "ENTREGADO".equalsIgnoreCase(estado_revision);
    }

    public boolean isBloqueadoPorRevision() {
        return !isEstadoEntregadoParaEditar();
    }

    public boolean puedeSolicitarRevision() {
        return isEditablePorReportero() && isEstadoEntregadoParaEditar();
    }

    public boolean isEditableMultimediaPorReportero() {
        return editable_multimedia != null && editable_multimedia == 1;
    }

    public String getResumenCambios() {
        boolean cambiaSubtitulo = subtitulo_guardado != null;
        boolean cambiaCuerpo = cuerpo_guardado != null;
        if (cambiaSubtitulo && cambiaCuerpo) {
            return "Subtítulo y cuerpo";
        } else if (cambiaSubtitulo) {
            return "Subtítulo";
        } else if (cambiaCuerpo) {
            return "Cuerpo";
        }
        return "Sin cambios registrados";
    }

    @Override
    public String toString() {
        if (ruta != null && !ruta.isBlank()) {
            if (nombre_reportero_multimedia != null && !nombre_reportero_multimedia.isBlank()) {
                return ruta + " (" + nombre_reportero_multimedia + ")";
            }
            return ruta;
        }
        return id_evento + " - " + descripcion;
    }
}