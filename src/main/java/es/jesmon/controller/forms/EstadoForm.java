package es.jesmon.controller.forms;

public class EstadoForm {

	private String idIncidenciaB64;
	private Integer idEstado;
	private String observaciones;
	private Long lgEmailAviso;
	
	public Long getLgEmailAviso() {
		return lgEmailAviso;
	}
	public void setLgEmailAviso(Long lgEmailAviso) {
		this.lgEmailAviso = lgEmailAviso;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Integer getIdEstado() {
		return idEstado;
	}
	public void setIdEstado(Integer idEstado) {
		this.idEstado = idEstado;
	}
	
	public String getIdIncidenciaB64() {
		return idIncidenciaB64;
	}
	public void setIdIncidenciaB64(String idIncidenciaB64) {
		this.idIncidenciaB64 = idIncidenciaB64;
	}
	
	
}
