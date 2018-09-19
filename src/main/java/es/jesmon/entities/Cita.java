package es.jesmon.entities;
// Generated 29-ene-2018 22:17:55 by Hibernate Tools 5.2.6.Final

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

/**
 * Tramitador generated by hbm2java
 */
@Entity
@Table(name = "cita", catalog = "jesmon")
public class Cita  implements java.io.Serializable {

	private static final long serialVersionUID = 6933726978664517149L;
	private Integer id_cita;
	
	private int estado;
	
	//private date intereses;
	 private Grupo grupoa; 
	 private Grupo grupob; 
	
	
	public Cita() {
	}

	public Cita( int estado) {
		
		this.estado = estado;
		
	}

	



	
	
	@Column(name = "estado", nullable = false, length = 11)
	public int getEstado() {
		return this.estado;
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id_cita", unique = true, nullable = false)

	public Integer getId_cita() {
		return id_cita;
	}

	public void setId_cita(Integer id_cita) {
		this.id_cita = id_cita;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_grupoa", nullable = false)
	
	public Grupo getGrupoa() {
		return grupoa;
	}

	public void setGrupoa(Grupo grupoa) {
		this.grupoa = grupoa;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_grupob", nullable = false)
	public Grupo getGrupob() {
		return grupob;
	}

	public void setGrupob(Grupo grupob) {
		this.grupob = grupob;
	}

	public boolean equals(Object o) {
		if(o == null || !o.getClass().equals(Cita.class))
			return false;

		Cita grupo = (Cita)o;
		return id_cita.equals(getId_cita());
	}

	
//    @ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "id_grupoa", nullable = false)
//	public Grupo getGrupoa() {
//		return grupoa;
//	}
//
//	public void setGrupoa(Grupo grupoa) {
//		this.grupoa = grupoa;
//	}
//	
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "id_grupob", nullable = false)
//	public Grupo getGrupob() {
//		return grupob;
//	}
//
//	public void setGrupob(Grupo grupob) {
//		this.grupob = grupob;
//	}
}
