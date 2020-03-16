package es.ucm.fdi.iw.services;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import es.ucm.fdi.iw.model.Usuario;
import es.ucm.fdi.iw.model.Usuario.Rol;


@Service
public class UsuariosService implements IUsuariosService{
	
	private List<Usuario> list;
	
	public UsuariosService() {
		list = getAllUsuariosFromDB();
	}

	@Override
	public Usuario getUsuario(long id) {
		for(Usuario u: list) if(u.getId() == id) return u;
		return null;
	}

	@Override
	public List<Usuario> getAllUsuarios() {
		return list;
	}

	//Este método debe cambiar su implementación para recoger los datos de la DB
	private List<Usuario> getAllUsuariosFromDB(){
		List<Usuario> list = new LinkedList<>();
		
		Usuario u1 = new Usuario();
		u1.setActivo((byte) 1);
		u1.setNombre("Pablo");
		u1.setRoles(Rol.USER.name());
		u1.setApellidos("Morientes Lavín");
		u1.setEdad(21);
		u1.setTags("WEB-JAVA-SPRING");
		
		list.add(u1);
		list.add(u1);
		list.add(u1);
		list.add(u1);
		list.add(u1);
		
		return list;
	}
	
}
