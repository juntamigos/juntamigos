package es.jesmon.config;

import es.jesmon.entities.JesmonEntity;

public class Admin extends JesmonEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8872420918004131753L;
	private byte[] password;

	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNombreCampoId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPassword(byte[] password) {
		this.password = password;
	}

	@Override
	public byte[] getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getNombreCompleto() {
		return "ADMINISTRADOR";
	}

}
