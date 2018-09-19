package es.jesmon.controller.forms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class IncidenciaForm {

	//@NotEmpty(message = "El campo asunto es obligatorio")
	@NotNull
    @Size(min=2, max=80)
	private String asunto;
	
	@NotNull
    @Size(min=2, max=30)
	private String texto;
	
	@NotNull(message = "El campo sede es obligatorio")
	private Integer idSede;
	
	private Integer idTipoIncidencia;
	private Integer idPrioridadIncidencia;
	
	public Integer getIdSede() {
		return idSede;
	}
	public void setIdSede(Integer idSede) {
		this.idSede = idSede;
	}
	public String getAsunto() {
		return asunto;
	}
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
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
