package es.jesmon.services.mensaje;

import java.util.List;

import es.jesmon.entities.Fichero;
import es.jesmon.entities.Mensaje;
import es.jesmon.services.exception.ServicesExpception;

public interface MensajesService {

	public void insertar(Mensaje mensaje, List<Fichero> listaFicheros)  throws ServicesExpception;
	
}
