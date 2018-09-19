package es.jesmon.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import es.jesmon.entities.Tramitador;

@Transactional
public interface TramitadorRepository extends CrudRepository<Tramitador, Integer> {

}