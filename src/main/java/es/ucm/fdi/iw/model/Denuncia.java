package es.ucm.fdi.iw.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id; 
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
	@NamedQuery(name="Denuncia.getLastThree", query="SELECT d FROM Denuncia d ORDER BY d.fecha"),

	@NamedQuery(name="Denuncia.getAllDenuncias", query="SELECT d FROM Denuncia d WHERE d.tramitada=false")
})
public class Denuncia {

    private long id;
    private Usuario denunciante;
    private Usuario denunciado;
    private String detalles;
    private boolean tramitada;
    private LocalDateTime fecha;

    public Denuncia() {
    }

    public Denuncia(long id, Usuario denunciante, Usuario denunciado, String detalles, boolean tramitada) {
        this.id = id;
        this.denunciante = denunciante;
        this.denunciado = denunciado;
        this.detalles = detalles;
        this.tramitada = tramitada;
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
    public Usuario getDenunciante() {
        return this.denunciante;
    }

    public void setDenunciante(Usuario denunciante) {
        this.denunciante = denunciante;
    }

    @ManyToOne(targetEntity = Usuario.class)
    public Usuario getDenunciado() {
        return this.denunciado;
    }

    public void setDenunciado(Usuario denunciado) {
        this.denunciado = denunciado;
    }

    public String getDetalles() {
        return this.detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    public boolean isTramitada() {
        return this.tramitada;
    }

    public void setTramitada(boolean tramitada) {
        this.tramitada = tramitada;
    }

    public Denuncia denunciante(Usuario denunciante) {
        this.denunciante = denunciante;
        return this;
    }

    public Denuncia denunciado(Usuario denunciado) {
        this.denunciado = denunciado;
        return this;
    }

    public Denuncia denuncia(String detalles) {
        this.detalles = detalles;
        return this;
    }

    public Denuncia tramitada(boolean tramitada) {
        this.tramitada = tramitada;
        return this;
    }

    @Override
    public String toString() {
        return "{" + " idDenuncia='" + getId() + "'" + ", denunciante='" + getDenunciante() + "'" + ", denunciado='"
                + getDenunciado() + "'" + ", denuncia='" + getDetalles() + "'" + ", tramitada='" + isTramitada() + "'"
                + "}";
    }

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}
}
