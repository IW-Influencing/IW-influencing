package es.ucm.fdi.iw.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

public class Denuncia {

    private long idDenuncia;
	private Usuario denunciante;
	private Usuario denunciado;
	private String denuncia;
	private boolean tramitada;

    public Denuncia() {
    }

    public Denuncia(long idDenuncia, Usuario denunciante, Usuario denunciado, String denuncia, boolean tramitada) {
        this.idDenuncia = idDenuncia;
        this.denunciante = denunciante;
        this.denunciado = denunciado;
        this.denuncia = denuncia;
        this.tramitada = tramitada;
    }

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getIdDenuncia() {
        return this.idDenuncia;
    }

    public void setIdDenuncia(long idDenuncia) {
        this.idDenuncia = idDenuncia;
    }

    @ManyToOne(targetEntity=Usuario.class)
    public Usuario getDenunciante() {
        return this.denunciante;
    }

    public void setDenunciante(Usuario denunciante) {
        this.denunciante = denunciante;
    }

    @ManyToOne(targetEntity=Usuario.class)
    public Usuario getDenunciado() {
        return this.denunciado;
    }

    public void setDenunciado(Usuario denunciado) {
        this.denunciado = denunciado;
    }

    public String getDenuncia() {
        return this.denuncia;
    }

    public void setDenuncia(String denuncia) {
        this.denuncia = denuncia;
    }

    public boolean isTramitada() {
        return this.tramitada;
    }

    public boolean getTramitada() {
        return this.tramitada;
    }

    public void setTramitada(boolean tramitada) {
        this.tramitada = tramitada;
    }

    public Denuncia idDenuncia(long idDenuncia) {
        this.idDenuncia = idDenuncia;
        return this;
    }

    public Denuncia denunciante(Usuario denunciante) {
        this.denunciante = denunciante;
        return this;
    }

    public Denuncia denunciado(Usuario denunciado) {
        this.denunciado = denunciado;
        return this;
    }

    public Denuncia denuncia(String denuncia) {
        this.denuncia = denuncia;
        return this;
    }

    public Denuncia tramitada(boolean tramitada) {
        this.tramitada = tramitada;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " idDenuncia='" + getIdDenuncia() + "'" +
            ", denunciante='" + getDenunciante() + "'" +
            ", denunciado='" + getDenunciado() + "'" +
            ", denuncia='" + getDenuncia() + "'" +
            ", tramitada='" + isTramitada() + "'" +
            "}";
    }
}
