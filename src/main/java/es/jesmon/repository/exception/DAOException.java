package es.jesmon.repository.exception;

public class DAOException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2474621676498597896L;
	
	public DAOException() {}
	
	
	public DAOException(String error) {
		super(error);
	}

	public DAOException(Exception error) {
		super(error);
	}
	
}
