package es.jesmon.repository.util;

public class AliasBean {

	public final static String LEFT_JOIN = "leftJoin";
	public final static String INNER_JOIN = "innerJoin";
	public final static String LEFT_OUTER_JOIN = "LEFT_OUTER_JOIN";
	
	private String nombreAlias;
	private String valorAlias;
	private String tipoJoin;

	public AliasBean (String nombreAlias, String valorAlias, String tipoJoin){
		this.nombreAlias = nombreAlias;
		this.valorAlias = valorAlias; 
		this.tipoJoin = tipoJoin;		
	}
	
	public AliasBean (){}
	
	
	public String getNombreAlias() {
		return nombreAlias;
	}

	public void setNombreAlias(String nombreAlias) {
		this.nombreAlias = nombreAlias;
	}

	public String getValorAlias() {
		return valorAlias;
	}

	public void setValorAlias(String valorAlias) {
		this.valorAlias = valorAlias;
	}

	public String getTipoJoin() {
		return tipoJoin;
	}
	public void setTipoJoin(String tipoJoin) {
		this.tipoJoin = tipoJoin;
	}
	
}
