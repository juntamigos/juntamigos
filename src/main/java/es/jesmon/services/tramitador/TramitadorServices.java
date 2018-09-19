package es.jesmon.services.tramitador;

import java.util.List;

import es.jesmon.entities.Tramitador;
import es.jesmon.services.exception.ServicesExpception;

public interface TramitadorServices {

	public List<Tramitador> getListaTramitadoresEmpresa(Integer idEmpresa) throws ServicesExpception;
	
	public List<Tramitador> getListaTramitadoresSede(Integer idSede) throws ServicesExpception;
}
