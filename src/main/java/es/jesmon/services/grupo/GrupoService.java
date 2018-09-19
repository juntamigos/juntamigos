package es.jesmon.services.grupo;

import es.jesmon.entities.JesmonEntity;
import es.jesmon.services.exception.ServicesExpception;

public interface GrupoService {
	
	public void setGrupoUsuario(JesmonEntity entity) throws ServicesExpception;

}
