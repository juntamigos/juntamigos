package es.jesmon.services.estadoIncidencia;

import java.util.List;

import es.jesmon.entities.EstadoIncidencia;
import es.jesmon.entities.Fichero;
import es.jesmon.services.exception.ServicesExpception;

public interface EstadoIncidenciaService {

	public void insertarEstadoIncidencia(EstadoIncidencia estadoIncidencia, List<Fichero> listaFicheros) throws ServicesExpception;
}
