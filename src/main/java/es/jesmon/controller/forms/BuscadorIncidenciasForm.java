package es.jesmon.controller.forms;

import java.io.Serializable;

public class BuscadorIncidenciasForm implements Serializable {

	private static final long serialVersionUID = -5100839990170875314L;
	private Integer idSede;
	private Integer idEstado;
	private Integer idEmpresa;
	private Integer idResponsable;
	private String fechaAltaDesde;
	private String fechaAltaHasta;
	private Integer idIncidencia;
	private Integer idTipoIncidencia;
	private Integer idPrioridadIncidencia;
	
	public Integer getIdIncidencia() {
		return idIncidencia;
	}
	public void setIdIncidencia(Integer idIncidencia) {
		this.idIncidencia = idIncidencia;
	}
	public Integer getIdSede() {
		return idSede;
	}
	public void setIdSede(Integer idSede) {
		this.idSede = idSede;
	}
	public Integer getIdEstado() {
		return idEstado;
	}
	public void setIdEstado(Integer idEstado) {
		this.idEstado = idEstado;
	}
	public Integer getIdEmpresa() {
		return idEmpresa;
	}
	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
	public Integer getIdResponsable() {
		return idResponsable;
	}
	public void setIdResponsable(Integer idResponsable) {
		this.idResponsable = idResponsable;
	}
	public String getFechaAltaDesde() {
		return fechaAltaDesde;
	}
	public void setFechaAltaDesde(String fechaAltaDesde) {
		this.fechaAltaDesde = fechaAltaDesde;
	}
	public String getFechaAltaHasta() {
		return fechaAltaHasta;
	}
	public void setFechaAltaHasta(String fechaAltaHasta) {
		this.fechaAltaHasta = fechaAltaHasta;
	}
	public Integer getIdTipoIncidencia() {
		return idTipoIncidencia;
	}
	public void setIdTipoIncidencia(Integer idTipoIncidencia) {
		this.idTipoIncidencia = idTipoIncidencia;
	}
	public Integer getIdPrioridadIncidencia() {
		return idPrioridadIncidencia;
	}
	public void setIdPrioridadIncidencia(Integer idPrioridadIncidencia) {
		this.idPrioridadIncidencia = idPrioridadIncidencia;
	}
	
}
