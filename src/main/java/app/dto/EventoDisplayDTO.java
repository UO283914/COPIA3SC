package app.dto;

public class EventoDisplayDTO {
	private Integer idEvento;
	private String descripcion;
	private String fecha;
	private String fechaInicio;
	private String fechaFin;
	private String tematicas;
	private String finalizado;

	public Integer getIdEvento() { return idEvento; }
	public void setIdEvento(Integer idEvento) { this.idEvento = idEvento; }
	public void setId_evento(Integer id_evento) { this.idEvento = id_evento; }

	public String getDescripcion() { return descripcion; }
	public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

	public String getFecha() { return fecha; }
	public void setFecha(String fecha) { this.fecha = fecha; }

	public String getFechaInicio() { return fechaInicio; }
	public void setFechaInicio(String fechaInicio) { this.fechaInicio = fechaInicio; }
	public void setFecha_inicio(String fecha_inicio) { this.fechaInicio = fecha_inicio; }

	public String getFechaFin() { return fechaFin; }
	public void setFechaFin(String fechaFin) { this.fechaFin = fechaFin; }
	public void setFecha_fin(String fecha_fin) { this.fechaFin = fecha_fin; }

	public String getTematicas() { return tematicas; }
	public void setTematicas(String tematicas) { this.tematicas = tematicas; }

	public String getFinalizado() { return finalizado; }
	public void setFinalizado(String finalizado) { this.finalizado = finalizado; }
}
