package es.ucm.fdi.iw.model;

import java.io.File;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Perfil_RRSS {

    private long idPerfil;
	private Usuario influencer;
	private String nombre;
	private String[] numSeguidores;
	private File imagen;

	public Perfil_RRSS() {
	}

	public Perfil_RRSS(long idPerfil, Usuario influencer, String nombre, String[] numSeguidores, File imagen) {
		this.idPerfil = idPerfil;
		this.influencer = influencer;
		this.nombre = nombre;
		this.numSeguidores = numSeguidores;
		this.imagen = imagen;
		
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getIdPerfil() {
		return this.idPerfil;
	}

	public void setIdPerfil(long idPerfil) {
		this.idPerfil = idPerfil;
	}

	@ManyToOne(targetEntity=Usuario.class)
	public Usuario getInfluencer() {
		return this.influencer;
	}

	public void setInfluencer(Usuario influencer) {
		this.influencer = influencer;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String[] getNumSeguidores() {
		return this.numSeguidores;
	}

	public void setNumSeguidores(String[] numSeguidores) {
		this.numSeguidores = numSeguidores;
	}

	public File getImagen() {
		return this.imagen;
	}

	public void setImagen(File imagen) {
		this.imagen = imagen;
	}

	@Override
	public String toString() {
		return "{" +
			" idPerfil='" + getIdPerfil() + "'" +
			", influencer='" + getInfluencer() + "'" +
			", nombre='" + getNombre() + "'" +
			", numSeguidores='" + getNumSeguidores() + "'" +
			", imagen='" + getImagen() + "'" +
			"}";
	}

}
