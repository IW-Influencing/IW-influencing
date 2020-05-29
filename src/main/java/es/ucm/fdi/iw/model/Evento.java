package es.ucm.fdi.iw.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import es.ucm.fdi.iw.model.Usuario.Rol;

@NamedQueries({
	@NamedQuery(name="Evento.adminEventsByDate", 
			query="SELECT e FROM Evento e WHERE leido = false AND e.tipo = 'ADMINISTRACION' ORDER BY e.fechaEnviado"),
	
	@NamedQuery(name="Evento.getChat",
		query="SELECT e FROM Evento e WHERE e.tipo = 'CHAT' AND e.candidatura.id = :idCandidatura ORDER BY e.fechaEnviado"),
	
	@NamedQuery(name="Evento.getNotificacionesUnread",
	query="SELECT e FROM Evento e WHERE e.tipo = 'NOTIFICACION' AND e.receptor.id = :idUsuario AND e.leido = false"),
})

@Entity
public class Evento {

	private long id;
	private String descripcion;
	private Usuario emisor;
	private Usuario receptor;
	public enum Tipo {CHAT, ADMINISTRACION, BUSQUEDA, NOTIFICACION};
	private String tipo;
	private Candidatura candidatura;
	private LocalDateTime fechaEnviado;
	private LocalDateTime fechaRecibido;
	private boolean leido;
	

	/**
	 * Convierte colecciones de mensajes a formato JSONificable
	 * @param events
	 * @return
	 * @throws JsonProcessingException
	 */
	public static List<TransferChat> asTransferObjects(Collection<Evento> events, Usuario u) {
		ArrayList<TransferChat> all = new ArrayList<>();
		for (Evento m : events) {
			TransferChat t = new TransferChat(m);
			t.setPropio(t.from.equals(u.getNombre()));
			if (u.hasRole(Rol.INFLUENCER)) {
				t.setNombreUsuario(m.getCandidatura().getPropuesta().getEmpresa().getNombre());
			}
			else {
				t.setNombreUsuario(m.getCandidatura().getCandidato().getNombre());
			}
			all.add(t);
		}
		return all;
	}
	
	/**
	 * Objeto para persistir a/de JSON
	 * @author mfreire
	 */
	
	public static class TransferChat {
		private String from;
		private String to;
		private String sent;
		private String received;
		private String text;
		private String nombrePropuesta;
		private String nombreUsuario;
		private boolean propio;
		long id;
		public TransferChat(Evento e) {
			this.from = e.getEmisor().getNombre();
			this.to = e.getReceptor().getNombre();
			this.sent = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(e.getFechaEnviado());
			this.received = e.getFechaRecibido() == null ?
					null : DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(e.getFechaRecibido());
			this.text = e.getDescripcion();
			this.nombrePropuesta = e.getCandidatura().getPropuesta().getNombre();
			this.id = e.getId();
		}
		public String getFrom() {
			return from;
		}
		public void setFrom(String from) {
			this.from = from;
		}
		public String getTo() {
			return to;
		}
		public void setTo(String to) {
			this.to = to;
		}
		public String getSent() {
			return sent;
		}
		public void setSent(String sent) {
			this.sent = sent;
		}
		public String getReceived() {
			return received;
		}
		public void setReceived(String received) {
			this.received = received;
		}
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		public boolean isPropio() {
			return propio;
		}
		public void setPropio(boolean propio) {
			this.propio = propio;
		}
		public String getNombrePropuesta() {
			return nombrePropuesta;
		}
		public void setNombrePropuesta(String nombrePropuesta) {
			this.nombrePropuesta = nombrePropuesta;
		}
		public String getNombreUsuario() {
			return nombreUsuario;
		}
		public void setNombreUsuario(String nombreUsuario) {
			this.nombreUsuario = nombreUsuario;
		}		
		
		

	}
	
	

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


	

	public LocalDateTime getFechaEnviado() {
		return fechaEnviado;
	}

	public void setFechaEnviado(LocalDateTime fechaEnviado) {
		this.fechaEnviado = fechaEnviado;
	}

	public LocalDateTime getFechaRecibido() {
		return fechaRecibido;
	}

	public void setFechaRecibido(LocalDateTime fechaRecibido) {
		this.fechaRecibido = fechaRecibido;
	}

	public boolean isLeido() {
		return leido;
	}

	public void setLeido(boolean leido) {
		this.leido = leido;
	}

	@ManyToOne(targetEntity = Candidatura.class)
	public Candidatura getCandidatura() {
		return candidatura;
	}

	public void setCandidatura(Candidatura candidatura) {
		this.candidatura = candidatura;
	}
	
	
	
	

}
