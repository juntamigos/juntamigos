package es.jesmon.services.sedes;

import java.util.List;

import es.jesmon.entities.Sede;
import es.jesmon.services.exception.ServicesExpception;

public interface SedesServices {

	public List<Sede> getListaSedes(Integer idEmpresa) throws ServicesExpception;
}
