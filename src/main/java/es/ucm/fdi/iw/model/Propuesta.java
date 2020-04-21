package es.ucm.fdi.iw.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@NamedQueries({
	
	@NamedQuery(name="Propuesta.getAllProposals",
	query="SELECT p FROM Propuesta p WHERE p.activa = true"),
	
@NamedQuery(name="Propuesta.searchByNombre",
query="SELECT p FROM Propuesta p "
		+ "WHERE p.nombre LIKE :nombre"),
})
public class Propuesta {

	private long id;
	private List<Candidatura> candidaturas;
	private Usuario empresa;
	private String nombre;
	private String tags;
	private String descripcion;
	private BigDecimal sueldo;
	private LocalDateTime fechaInicio;
	private LocalDateTime fechaFin;
	public enum Modo {VISTA, CREACION, ULTIMATUM};
	private String modo;
	private Boolean activa;

	public Boolean isActiva() {
		return this.activa;
	}

	public void setActiva(Boolean activa) {
		this.activa = activa;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	

	public LocalDateTime getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDateTime fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDateTime getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDateTime fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getModo() {
		return modo;
	}

	public void setModo(String modo) {
		this.modo = modo;
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

	public BigDecimal getSueldo() {
		return sueldo;
	}

	public void setSueldo(BigDecimal sueldo) {
		this.sueldo = sueldo;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}
	
	public boolean hasTag(String tag) {
		return Arrays.stream(tags.split(","))
				.anyMatch(r -> r.equals(tag));
	}


}
