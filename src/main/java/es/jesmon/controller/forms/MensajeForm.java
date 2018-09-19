package es.jesmon.controller.forms;

import java.util.Base64;

public class MensajeForm {

	private String idIncidenciaB64;
	private String texto;
	private Integer lgInterno = 0;
	private Integer lgEnviarCorreo;
	
	public String getIdIncidenciaB64() {
		return idIncidenciaB64;
	}
	public void setIdIncidenciaB64(String idIncidenciaB64) {
		this.idIncidenciaB64 = idIncidenciaB64;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public Integer getLgInterno() {
		return lgInterno;
	}
	public void setLgInterno(Integer lgInterno) {
		this.lgInterno = lgInterno;
	}
	public Integer getLgEnviarCorreo() {
		return lgEnviarCorreo;
	}
	public void setLgEnviarCorreo(Integer lgEnviarCorreo) {
		this.lgEnviarCorreo = lgEnviarCorreo;
	}
	
	public Integer getIdIncidencia() {
		if(idIncidenciaB64 == null)
			return null;
		return new Integer(new String(Base64.getDecoder().decode(idIncidenciaB64.toString())));
	}
}
