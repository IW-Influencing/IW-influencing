package es.ucm.fdi.iw.services;

import java.util.List;

import es.ucm.fdi.iw.model.Usuario;

public interface IUsuariosService {
	public Usuario getUsuario(long id);
	public List<Usuario> getAllUsuarios();
}
