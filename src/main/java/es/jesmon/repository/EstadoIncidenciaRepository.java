package es.jesmon.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import es.jesmon.entities.EstadoIncidencia;

@Transactional
public interface EstadoIncidenciaRepository extends CrudRepository<EstadoIncidencia, Integer> {

}