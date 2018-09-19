package es.jesmon.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import es.jesmon.entities.Estado;
import es.jesmon.entities.Incidencia;
import es.jesmon.entities.Sede;
import es.jesmon.repository.filter.FiltroIncidencia;


@Transactional
public interface IncidenciaRepository extends CrudRepository<Incidencia, Integer> {

	public static Specification<Incidencia> cumpleFiltro(final FiltroIncidencia filtro) {
		return new Specification<Incidencia>() {
			@Override
			public Predicate toPredicate(final Root<Incidencia> root, final CriteriaQuery<?> query,
					final CriteriaBuilder builder) {
				Join<Incidencia, Estado> estadoIncidencia = root.join("estadoIncidencia").join("estado");
				Join<Incidencia, Sede> sede = root.join("sede");
				
				final List<Predicate> predicates = new ArrayList<>();
				if (filtro.getIdEstado() != null) {
					predicates.add(builder.equal(estadoIncidencia.get("estado.idEstado"), filtro.getIdEstado()));
				}
				
				if (filtro.getIdSede() != null) {
					predicates.add(builder.equal(sede.get("sede.idSede"), filtro.getIdEstado()));
				}
				
				return builder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
	}
}
