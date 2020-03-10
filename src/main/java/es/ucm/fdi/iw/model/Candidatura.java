package es.ucm.fdi.iw.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Candidatura {

	private long idCandidatura;
	private Propuesta propuesta;
	private Usuario candidato;
	private Boolean aceptada;
	public enum Estado {EN_CURSO, FINALIZADA};
	private Estado estado;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getId() {
		return idCandidatura;
	}

	public void setId(long id) {
		this.idCandidatura = id;
	}

	@ManyToOne(targetEntity = Propuesta.class)
	public Propuesta getPropuesta() {
		return propuesta;
	}

	public void setPropuesta(Propuesta propuesta) {
		this.propuesta = propuesta;
	}

	@ManyToOne(targetEntity = Usuario.class)
	public Usuario getCandidato() {
		return candidato;
	}

	public void setCandidato(Usuario candidato) {
		this.candidato = candidato;
	}

	public Boolean getAceptada() {
		return aceptada;
	}

	public void setAceptada(Boolean aceptada) {
		this.aceptada = aceptada;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "Candidatura #" + idCandidatura;
	}

}
