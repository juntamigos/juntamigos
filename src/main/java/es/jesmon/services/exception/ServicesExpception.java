package es.jesmon.services.exception;

public class ServicesExpception extends Exception {

	private static final long serialVersionUID = 2182013330234959596L;

	public ServicesExpception() {
		
	}
	
	public ServicesExpception(Exception e) {
		super(e);
	}
	
}
