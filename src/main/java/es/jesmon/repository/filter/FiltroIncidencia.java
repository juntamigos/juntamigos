package es.jesmon.repository.filter;

public class FiltroIncidencia extends ListParams {

	private Long idEstado;
	private Long idSede;
	
	public Long getIdEstado() {
		return idEstado;
	}
	public void setIdEstado(Long idEstado) {
		this.idEstado = idEstado;
	}
	public Long getIdSede() {
		return idSede;
	}
	public void setIdSede(Long idSede) {
		this.idSede = idSede;
	}
	
}