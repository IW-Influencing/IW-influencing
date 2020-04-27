package es.ucm.fdi.iw.model;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class PerfilRRSS {

	private long id;
	private Usuario influencer;
	private String nombre;
	private int numSeguidores;


	public PerfilRRSS(long id, Usuario influencer, String nombre, int numSeguidores) {
		this.id = id;
		this.influencer = influencer;
		this.nombre = nombre;
		this.numSeguidores = numSeguidores;
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

	public int getNumSeguidores() {
		return this.numSeguidores;
	}

	public void setNumSeguidores(int numSeguidores) {
		this.numSeguidores = numSeguidores;
	}

}
