package es.jesmon.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import es.jesmon.entities.Responsable;

@Transactional
public interface ResponsableRepository extends CrudRepository<Responsable, Integer> {

}
