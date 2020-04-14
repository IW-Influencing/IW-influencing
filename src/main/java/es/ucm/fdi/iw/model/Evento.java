package es.ucm.fdi.iw.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@NamedQueries({
	@NamedQuery(name="Evento.adminEventsByDate", 
			query="SELECT e FROM Evento e WHERE e.receptor.id = :idUsuario AND e.tipo = 'ADMINISTRACION' ORDER BY e.fecha"),

	@NamedQuery(name="Evento.searchesByDate",
		query="SELECT e FROM Evento e WHERE e.receptor.id = :idUsuario AND e.tipo = 'BUSQUEDA' ORDER BY e.fecha"),
})

@Entity
public class Evento {

	private long id;
	private String descripcion;
	private Usuario emisor;
	private Usuario receptor;
	public enum Tipo {CHAT, PRIVADO, ADMINISTRACION, BUSQUEDA, NOTIFICACION};
	private Propuesta propuesta;
	private String tipo;
	private LocalDateTime fecha;
	private boolean leido;
	


	//Incluir fecha

	// puede ser interesante ver cómo funcionan en la plantilla 
	// https://github.com/manuel-freire/iw1920/blob/master/plantilla/src/main/java/es/ucm/fdi/iw/model/Message.java
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@ManyToOne(targetEntity = Usuario.class)
	public Usuario getEmisor() {
		return emisor;
	}

	public void setEmisor(Usuario emisor) {
		this.emisor = emisor;
	}

	@ManyToOne(targetEntity = Usuario.class)
	public Usuario getReceptor() {
		return receptor;
	}

	public void setReceptor(Usuario receptor) {
		this.receptor = receptor;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public void setTipo(Tipo tipo) {
		this.tipo = tipo.toString();
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public boolean isLeido() {
		return leido;
	}

	public void setLeido(boolean leido) {
		this.leido = leido;
	}

	@ManyToOne(targetEntity = Propuesta.class)
	public Propuesta getPropuesta() {
		return propuesta;
	}

	public void setPropuesta(Propuesta propuesta) {
		this.propuesta = propuesta;
	}
	
	
	
	

}
