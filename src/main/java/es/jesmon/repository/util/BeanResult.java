package es.jesmon.repository.util;


public class BeanResult {
	
	private BeanList lista;
	private Integer numRegPagina;
	private String orden;
	private String tipoOrden;
	private Integer pagina;
	private boolean export;
	private String tipoExportar;
	
	public BeanList getLista() {
		return lista;
	}
	public void setLista(BeanList lista) {
		this.lista = lista;
	}
	public Integer getNumRegPagina() {
		return numRegPagina;
	}
	public void setNumRegPagina(Integer numRegPagina) {
		this.numRegPagina = numRegPagina;
	}	
	public String getOrden() {
		return orden;
	}
	public void setOrden(String orden) {
		this.orden = orden;
	}
	public Integer getPagina() {
		return pagina;
	}
	public void setPagina(Integer pagina) {
		this.pagina = pagina;
	}
	public boolean isExport() {
		return export;
	}
	public void setExport(boolean export) {
		this.export = export;
	}
	public String getTipoOrden() {
		return tipoOrden;
	}
	public void setTipoOrden(String tipoOrden) {
		this.tipoOrden = tipoOrden;
	}
	public String getTipoExportar() {
		return tipoExportar;
	}
	public void setTipoExportar(String tipoExportar) {
		this.tipoExportar = tipoExportar;
	}
	
	public Integer getTamanioPagina() {
		return isExport() ? lista.getNumRegistros() : numRegPagina; 
	}
	
	public BeanResult (){
		lista = new BeanList ();
	}
}
