package es.ucm.fdi.iw.control;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.ucm.fdi.iw.model.Candidatura;
import es.ucm.fdi.iw.model.PerfilRRSS;
import es.ucm.fdi.iw.model.Usuario;

@Controller()
@RequestMapping("perfil")
public class PerfilController {

    @Autowired
    private EntityManager entityManager;

	private static final Logger log = LogManager.getLogger(PerfilController.class);

    
    @GetMapping("")
    public String perfil(Model model, HttpSession session, @RequestParam long idUsuario) {
        Usuario u = entityManager.find(Usuario.class, idUsuario);
        List<PerfilRRSS>perfiles = entityManager.createNamedQuery("PerfilRRSS.byInfluencer", PerfilRRSS.class)
        		.setParameter("idUsuario",idUsuario).getResultList();
        
        model.addAttribute("modo", "VISTA");
        model.addAttribute("usuario", u);
        model.addAttribute("perfilesRRSS",perfiles); 		
        		
        return "modals/perfil";
    }

    @GetMapping("/edicion")
    public String edicionPerfil(Model model, HttpSession session) {
        Usuario u = entityManager.find(Usuario.class, ((Usuario) session.getAttribute("u")).getId());
        model.addAttribute("modo", "EDICION");
        model.addAttribute("usuario", u);
        return "modals/perfil";
    }

    @GetMapping("/creacion")
    public String creacion(Model model, HttpSession session) {
        model.addAttribute("modo", "CREACION");
        model.addAttribute("usuario",null);
        return "modals/perfil";
    }
    
    @PostMapping("/registra")
    @Transactional
    public String registraUsuario(RedirectAttributes redirectAttributes, Model model, @RequestParam String nombreCuenta, @RequestParam String nombre,
    		@RequestParam String pass1,@RequestParam String pass2, 
    		@RequestParam MultipartFile imagenPerfil, @RequestParam String tipoCuenta, 
    		@RequestParam String nombreTwitter, @RequestParam String seguidoresTwitter,
    		@RequestParam String nombreFacebook, @RequestParam String seguidoresFacebook,
    		@RequestParam String nombreInstagram, @RequestParam String seguidoresInstagram,
    		@RequestParam String nombreYoutube, @RequestParam String seguidoresYoutube){

    	String mensaje = "";
        List<Usuario>usuarios = entityManager.createNamedQuery("Usuario.byNombreCuenta", Usuario.class)
        		.setParameter("nombreCuenta",nombreCuenta).getResultList();
        
        if(usuarios.size() > 0) {
        	//Mostrar mensaje de error al usuario
        	mensaje = "Error. Ya existe un usuario con ese nombre de cuenta";
        }
        else {
        	if (pass1.equals(pass2)) {
        		if (nombreCuenta.equalsIgnoreCase(Usuario.Rol.INFLUENCER.toString())) {
	        		if(validaPerfiles(nombreTwitter, seguidoresTwitter, nombreFacebook, seguidoresFacebook, 
	        				nombreInstagram, seguidoresInstagram, nombreYoutube, seguidoresYoutube)) {
	        			Usuario u = new Usuario();
	    	        	u.setNombreCuenta(nombreCuenta);
	    	        	u.setNombre(nombre);
	    	        	u.setPassword(Usuario.encodePassword(pass1));
	    	        	u.setRoles("Usuario,Influencer");
	    	        	u.setActivo(Byte.MAX_VALUE);
	    	        	entityManager.persist(u);
	    	        	insertaPerfiles(nombreTwitter, seguidoresTwitter, nombreFacebook, seguidoresFacebook, 
	        				nombreInstagram, seguidoresInstagram, nombreYoutube, seguidoresYoutube, u);
	    	        	mensaje="Usuario registrado correctamente";
	        		}
	        		else
	        			//Mostrar mensaje de error al usuario
	                	mensaje = "Error. Perfiles de RRSS no válidos";
        		}
        		else {
        			Usuario u = new Usuario();
    	        	u.setNombreCuenta(nombreCuenta);
    	        	u.setNombre(nombre);
    	        	u.setCandidaturas(new ArrayList<>());
    	        	u.setActivo(Byte.valueOf("1"));
    	        	u.setRoles("Usuario,Empresa");
    	        	u.setPassword(Usuario.encodePassword(pass1));
    	        	entityManager.persist(u);
    	        	entityManager.flush();
    	        	mensaje="Usuario registrado correctamente";
        		}
        		
        	}
        	else
        	//Mostrar mensaje de error al usuario
        		mensaje = "Error. Las contraseñas no coinciden";
        }
        List<Usuario> x = entityManager.createNamedQuery("Usuario.getAllUsers", Usuario.class).getResultList();

        model.addAttribute("mensaje", mensaje);
        return "redirect:login";
        }
    
    @Transactional
	private void insertaPerfiles(String nombreTwitter, String seguidoresTwitter, String nombreFacebook,
			String seguidoresFacebook, String nombreInstagram, String seguidoresInstagram, String nombreYoutube,
			String seguidoresYoutube, Usuario u) {
    	
    	PerfilRRSS p = parseaPerfil(nombreTwitter, seguidoresTwitter, u, PerfilRRSS.RRSS.Twitter.toString());
    	if (p!=null)
    		entityManager.persist(p);
    	p = parseaPerfil(nombreYoutube, seguidoresYoutube, u, PerfilRRSS.RRSS.Youtube.toString());
    	if (p!=null)
    		entityManager.persist(p);
    	p = parseaPerfil(nombreInstagram, seguidoresInstagram, u, PerfilRRSS.RRSS.Instagram.toString());
    	if (p!=null)
    		entityManager.persist(p);
    	p = parseaPerfil(nombreFacebook, seguidoresFacebook, u, PerfilRRSS.RRSS.Facebook.toString());
    	if (p!=null)
    		entityManager.persist(p);
	}

	private boolean validaPerfiles(String nombreTwitter, String seguidoresTwitter, String nombreFacebook,
			String seguidoresFacebook, String nombreInstagram, String seguidoresInstagram, String nombreYoutube,
			String seguidoresYoutube) {
		
		boolean valido = false;
		if (nombreTwitter.length() > 0) {
			valido = (entityManager.createNamedQuery("PerfilRRSS.byNombre", PerfilRRSS.class)
	        		.setParameter("nombre",nombreTwitter).setParameter("rrss", 
	        				PerfilRRSS.RRSS.Twitter.toString()).getResultList().isEmpty() &&  seguidoresTwitter.length() > 0);
			if (!valido)
				return false;
		}
		if (nombreFacebook.length() > 0) {
			valido = (entityManager.createNamedQuery("PerfilRRSS.byNombre", PerfilRRSS.class)
	        		.setParameter("nombre",nombreFacebook).setParameter("rrss",
	        				PerfilRRSS.RRSS.Facebook.toString()).getResultList().isEmpty() && seguidoresFacebook.length()>0);
			if (!valido)
				return false;
		}
		if (nombreInstagram.length() > 0) {
			valido = (entityManager.createNamedQuery("PerfilRRSS.byNombre", PerfilRRSS.class)
	        		.setParameter("nombre",nombreInstagram).setParameter("rrss",
	        				PerfilRRSS.RRSS.Instagram.toString()).getResultList().isEmpty() && seguidoresInstagram.length()>0);
			if (!valido)
				return false;
		}
		if (nombreYoutube.length() > 0) {
			valido = (entityManager.createNamedQuery("PerfilRRSS.byNombre", PerfilRRSS.class)
	        		.setParameter("nombre",nombreYoutube).setParameter("rrss",
	        				PerfilRRSS.RRSS.Youtube.toString()).getResultList().isEmpty() && seguidoresYoutube.length()>0);
		}
		
		return valido;
		
	}

	private PerfilRRSS parseaPerfil(String nombre, String seguidores, Usuario u, String rrss) {
		// TODO Auto-generated method stub
		PerfilRRSS retorno = new PerfilRRSS();
		if (nombre.length()>0) {
			retorno.setNombre(nombre);
			retorno.setNumSeguidores(Integer.valueOf(seguidores));
			retorno.setInfluencer(u);
			retorno.setRrss(rrss);
			return retorno;
		}
		return null;
	}

}
