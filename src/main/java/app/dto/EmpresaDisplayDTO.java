package app.dto;

public class EmpresaDisplayDTO {
	private Integer idEmpresa;
	private String nombre;
	private Integer tieneAcceso;
	private Integer descargado;
	private Integer tieneTarifa;
	private Integer alCorrientePago;
	private Integer reportajePagado;
	private Integer elegiblePago;
	private String acceso;
	private String descarga;
	private String tarifa;
	private String pago;

	public Integer getIdEmpresa() { return idEmpresa; }
	public void setIdEmpresa(Integer idEmpresa) { this.idEmpresa = idEmpresa; }

	// TRUCO: Puerta secreta para SQLite
	public void setId_empresa(Integer id_empresa) { this.idEmpresa = id_empresa; }

	public String getNombre() { return nombre; }
	public void setNombre(String nombre) { this.nombre = nombre; }

	public Integer getTieneAcceso() { return tieneAcceso; }
	public void setTieneAcceso(Integer tieneAcceso) {
		this.tieneAcceso = tieneAcceso;
		this.acceso = (tieneAcceso != null && tieneAcceso == 1) ? "SÍ" : "NO";
	}

	public Integer getDescargado() { return descargado; }
	public void setDescargado(Integer descargado) {
		this.descargado = descargado;
		this.descarga = (descargado != null && descargado == 1) ? "DESCARGADO" : "NO DESCARGADO";
	}

	public String getAcceso() { return acceso; }
	public String getDescarga() { return descarga; }

	public Integer getTieneTarifa() { return tieneTarifa; }
	public void setTieneTarifa(Integer tieneTarifa) {
		this.tieneTarifa = tieneTarifa;
		this.tarifa = (tieneTarifa != null && tieneTarifa == 1) ? "SÍ" : "NO";
	}

	public Integer getAlCorrientePago() { return alCorrientePago; }
	public void setAlCorrientePago(Integer alCorrientePago) { this.alCorrientePago = alCorrientePago; }

	public Integer getReportajePagado() { return reportajePagado; }
	public void setReportajePagado(Integer reportajePagado) {
		this.reportajePagado = reportajePagado;
		actualizarPago();
	}

	public Integer getElegiblePago() { return elegiblePago; }
	public void setElegiblePago(Integer elegiblePago) {
		this.elegiblePago = elegiblePago;
		actualizarPago();
	}

	public String getTarifa() { return tarifa; }
	public String getPago() { return pago; }

	private void actualizarPago() {
		this.pago = (elegiblePago != null && elegiblePago == 1) ? "SÍ" : "NO";
	}
}
