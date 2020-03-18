package es.ucm.fdi.iw.model;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Propuesta {

	private long id;
	private List<Candidatura> candidaturas;
	private Usuario empresa;
	private String nombre;
	private String tags;
	private String descripcion;
	private double sueldo;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	@OneToMany(targetEntity=Candidatura.class)
	@JoinColumn(name="propuesta_id")
	public List<Candidatura> getCandidaturas() {
		return candidaturas;
	}

	public void setCandidaturas(List<Candidatura> candidaturas) {
		this.candidaturas = candidaturas;
	}

	@ManyToOne(targetEntity = Usuario.class)
	public Usuario getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Usuario empresa) {
		this.empresa = empresa;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public double getSueldo() {
		return sueldo;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public void setSueldo(double sueldo) {
		this.sueldo = sueldo;
	}
	
	public boolean hasTag(String tag) {
		return Arrays.stream(tags.split(","))
				.anyMatch(r -> r.equals(tag));
	}


}
