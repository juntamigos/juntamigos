package es.jesmon.services.incidencias;

import java.util.List;

import es.jesmon.entities.Fichero;
import es.jesmon.entities.Incidencia;
import es.jesmon.entities.PrioridadIncidencia;
import es.jesmon.entities.TipoIncidencia;
import es.jesmon.services.exception.ServicesExpception;

public interface IncidenciasService {

	public void insertar(Incidencia incidencia, List<Fichero> listaFicheros)  throws ServicesExpception;
	
	public List<TipoIncidencia> getListaTiposIncidencia() throws ServicesExpception;
	
	public List<PrioridadIncidencia> getListaPrioridadesIncidencia() throws ServicesExpception;
	
}
