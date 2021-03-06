package es.jesmon.entities;
// Generated 29-ene-2018 22:17:55 by Hibernate Tools 5.2.6.Final

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Base64;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

/**
 * EstadoIncidencia generated by hbm2java
 */
@Entity
@Table(name = "estado_incidencia", catalog = "jesmon")
public class EstadoIncidencia implements java.io.Serializable {

	private static final long serialVersionUID = -1616823260124658467L;
	private Integer idEstadoIncidencia;
	private Estado estado;
	private Responsable responsable;
	private Tramitador tramitador;
	private String observaciones;
	private Date fechaEstado;
	private Incidencia incidencia;
	private Set<FicheroBasico> ficheros = new HashSet<FicheroBasico>(0);

	public EstadoIncidencia() {
	}

	public EstadoIncidencia(Estado estado, Date fechaEstado) {
		this.estado = estado;
		this.fechaEstado = fechaEstado;
	}

	public EstadoIncidencia(Estado estado, Responsable responsable, Tramitador tramitador, String observaciones,
			Date fechaEstado) {
		this.estado = estado;
		this.responsable = responsable;
		this.tramitador = tramitador;
		this.observaciones = observaciones;
		this.fechaEstado = fechaEstado;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id_estado_incidencia", unique = true, nullable = false)
	public Integer getIdEstadoIncidencia() {
		return this.idEstadoIncidencia;
	}

	public void setIdEstadoIncidencia(Integer idEstadoIncidencia) {
		this.idEstadoIncidencia = idEstadoIncidencia;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_estado", nullable = false)
	public Estado getEstado() {
		return this.estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_responsable")
	public Responsable getResponsable() {
		return this.responsable;
	}

	public void setResponsable(Responsable responsable) {
		this.responsable = responsable;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tramitador")
	public Tramitador getTramitador() {
		return this.tramitador;
	}

	public void setTramitador(Tramitador tramitador) {
		this.tramitador = tramitador;
	}

	@Column(name = "observaciones", length = 4000)
	public String getObservaciones() {
		return this.observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_estado", nullable = false, length = 19)
	public Date getFechaEstado() {
		return this.fechaEstado;
	}

	public void setFechaEstado(Date fechaEstado) {
		this.fechaEstado = fechaEstado;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_incidencia")
	public Incidencia getIncidencia() {
		return incidencia;
	}

	public void setIncidencia(Incidencia incidencia) {
		this.incidencia = incidencia;
	}
	
	@Transient
	public String getNombreUsrAlta() {
		if(tramitador != null)
			return tramitador.getNombreCompleto();
		else
			return responsable.getNombreCompleto();
		
	}
	
	@Transient
	public String getObservacionesCorto () {
		if(StringUtils.isBlank(observaciones))
			return "";
		if(observaciones.length() < 20)
			return observaciones;
		else
			return StringUtils.substring(observaciones, 0, 20) + "...";
	}
	
	@Transient
	public String getObservacionesB64() {
		if(StringUtils.isBlank(observaciones))
			return "";
		return Base64.getEncoder().encodeToString(observaciones.getBytes());
	}
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "rel_fichero_estado_incidecia", catalog = "jesmon", joinColumns = {
			@JoinColumn(name = "id_estado", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "id_fichero", nullable = false, updatable = false) })
	public Set<FicheroBasico> getFicheros() {
		return this.ficheros;
	}

	public void setFicheros(Set<FicheroBasico> ficheros) {
		this.ficheros = ficheros;
	}

}
