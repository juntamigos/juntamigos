package es.jesmon.entities;
// Generated 29-ene-2018 22:17:55 by Hibernate Tools 5.2.6.Final

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Provincia generated by hbm2java
 */
@Entity
@Table(name = "prioridad_incidencia", catalog = "jesmon")
@Cacheable

public class PrioridadIncidencia implements java.io.Serializable {

	private static final long serialVersionUID = 7386449527344923091L;
	private Integer idPrioridadIncidencia;
	private String denominacion;
	private Set<Incidencia> incidencias = new HashSet<Incidencia>(0);

	public PrioridadIncidencia() {
	}

	public PrioridadIncidencia(Integer idPrioridadIncidencia, String denominacion) {
		this.idPrioridadIncidencia = idPrioridadIncidencia;
		this.denominacion = denominacion;
	}
	
	public PrioridadIncidencia(Integer idPrioridadIncidencia) {
		this.idPrioridadIncidencia = idPrioridadIncidencia;
	}

	public PrioridadIncidencia(Integer idPrioridadIncidencia, String denominacion, Set<Incidencia> incidencias) {
		this.idPrioridadIncidencia = idPrioridadIncidencia;
		this.denominacion = denominacion;
		this.incidencias = incidencias;
	}

	@Id

	@Column(name = "idprioridad_incidencia", unique = true, nullable = false)
	public Integer getIdPrioridadIncidencia() {
		return this.idPrioridadIncidencia;
	}

	public void setIdPrioridadIncidencia(Integer idPrioridadIncidencia) {
		this.idPrioridadIncidencia = idPrioridadIncidencia;
	}

	@Column(name = "denominacion", nullable = false, length = 100)
	public String getDenominacion() {
		return this.denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "prioridadIncidencia")
	public Set<Incidencia> getIncidencias() {
		return this.incidencias;
	}

	public void setIncidencias(Set<Incidencia> incidencias) {
		this.incidencias = incidencias;
	}

}
