package es.ucm.fdi.iw.model;

import java.io.File;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
	@NamedQuery(name="Usuario.byNombreUsuario",
	query="SELECT u FROM Usuario u "
			+ "WHERE u.nombre = :nombre AND u.activo = 1"),
	@NamedQuery(name="Usuario.hasNombre",
	query="SELECT COUNT(u) "
			+ "FROM Usuario u "
			+ "WHERE u.nombre = :nombre")
})
public class Usuario {

	private long id; // Id unico para cada usuario
	private byte activo;
	private String nombre;
	private String contraseña;
	public enum Rol {ADMIN, INFLUENCER, EMPRESA};
	private Rol rol; // Aqui se almacena el rol que tiene el usuario en la aplicación mediante números
	private String apellidos;
	private int edad;
	private String[] tags; // Se almacenan los tags
	private File imagen; // Se almacena la ruta en la que está almacenada la imagen

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
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

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public byte getActivo() {
		return activo;
	}

	public void setActivo(byte activo) {
		this.activo = activo;
	}

}
