package es.ucm.fdi.iw.model;

import java.io.File;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Perfil {

	private long id;
	private Usuario influencer;
	private String nombre;
	private String[] numSeguidores;
	private File imagen;

	public Perfil() {
	}

	public Perfil(long id, Usuario influencer, String nombre, String[] numSeguidores, File imagen) {
		this.id = id;
		this.influencer = influencer;
		this.nombre = nombre;
		this.numSeguidores = numSeguidores;
		this.imagen = imagen;

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@ManyToOne(targetEntity = Usuario.class)
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
		return "{" + " idPerfil='" + getId() + "'" + ", influencer='" + getInfluencer() + "'" + ", nombre='"
				+ getNombre() + "'" + ", numSeguidores='" + getNumSeguidores() + "'" + ", imagen='" + getImagen() + "'"
				+ "}";
	}

}
