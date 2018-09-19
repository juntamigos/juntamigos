package es.jesmon.services.mensaje.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import es.jesmon.entities.Fichero;
import es.jesmon.entities.Mensaje;
import es.jesmon.repository.JesmonRepository;
import es.jesmon.services.exception.ServicesExpception;
import es.jesmon.services.mensaje.MensajesService;

@Service
public class MensajesServiceImpl implements MensajesService {

	@Autowired
	JesmonRepository jesmonRepository;
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = ServicesExpception.class)
	public void insertar(Mensaje mensaje, List<Fichero> listaFicheros) throws ServicesExpception {
		try {
			jesmonRepository.insertar(mensaje);
			if(listaFicheros != null) {
				for(Fichero fichero : listaFicheros){
					fichero.getMensajes().add(mensaje);
					jesmonRepository.insertar(fichero);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ServicesExpception(e);
		}
	}

}
