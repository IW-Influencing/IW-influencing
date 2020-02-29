package es.ucm.fdi.iw.model;

import java.io.File;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Usuario {
	private long idUsuario;			//Id unico para cada usuario
	private int rol;				//Aqui se almacena el rol que tiene el usuario en la aplicación mediante números
	private String nombre;
	private String apellidos;
	private int edad;
	private String[] tags;			//Se almacenan los tags
	private File imagen;			//Se almacena la ruta en la que está almacenada la imagen
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getIdUsuario(){
		return idUsuario;
	}

	public int getRol() {
		return rol;
	}

	public void setRol(int rol) {
		this.rol = rol;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}
	
	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}

	public File getImagen() {
		return imagen;
	}

	public void setImagen(File imagen) {
		this.imagen = imagen;
	}
	
	
	
	
}

