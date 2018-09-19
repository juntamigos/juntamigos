package es.jesmon.services.empresa;

import es.jesmon.entities.JesmonEntity;
import es.jesmon.services.exception.ServicesExpception;

public interface EmpresaService {
	
	public void setEmpresasUsuario(JesmonEntity entity) throws ServicesExpception;

}
