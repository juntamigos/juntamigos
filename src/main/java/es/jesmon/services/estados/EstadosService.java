package es.jesmon.services.estados;

import java.util.List;

import es.jesmon.entities.Estado;
import es.jesmon.services.exception.ServicesExpception;

public interface EstadosService {

	public List<Estado> getListaEstados() throws ServicesExpception;
	
}
