package app.dto;

public class ReporteroDisplayDTO {
	private Integer idReportero;
	private String nombre;
	private String tematicas;
	private String tipoReportero;

	public Integer getIdReportero() { return idReportero; }
	public void setIdReportero(Integer idReportero) { this.idReportero = idReportero; }
	public void setId_reportero(Integer id_reportero) { this.idReportero = id_reportero; }

	public String getNombre() { return nombre; }
	public void setNombre(String nombre) { this.nombre = nombre; }

	public String getTematicas() { return tematicas; }
	public void setTematicas(String tematicas) { this.tematicas = tematicas; }

	public String getTipoReportero() { return tipoReportero; }
	public void setTipoReportero(String tipoReportero) { this.tipoReportero = tipoReportero; }
	public void setTipo_reportero(String tipo_reportero) { this.tipoReportero = tipo_reportero; }
}
