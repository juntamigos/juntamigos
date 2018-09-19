package es.jesmon.controller.forms;

public class TramitadorForm {

	private Integer idTramitador;
	private String user;
	private String email;
	private String password;
	public Integer getIdTramitador() {
		return idTramitador;
	}
	public void setIdTramitador(Integer idTramitador) {
		this.idTramitador = idTramitador;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String login) {
		this.user = login;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}