package es.ucm.fdi.iw.control;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.ucm.fdi.iw.LocalData;
import es.ucm.fdi.iw.model.PerfilRRSS;
import es.ucm.fdi.iw.model.Usuario;
import es.ucm.fdi.iw.model.Usuario.Rol;

@Controller()
@RequestMapping("perfil")
public class PerfilController {

	@Autowired
	private LocalData localData;
	
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
	public void registraUsuario(HttpSession session, HttpServletResponse response, Model model, 
			@RequestParam String nombreCuenta, @RequestParam String nombre,
    		@RequestParam String pass1,@RequestParam String pass2, 
    		@RequestParam MultipartFile imagenPerfil, @RequestParam String tipoCuenta,
    		@RequestParam String edad,	@RequestParam String nombreTwitter, @RequestParam String seguidoresTwitter,
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
        		if (tipoCuenta.equalsIgnoreCase(Usuario.Rol.INFLUENCER.toString())) {
        			if (edad.length()>0) {
		        		if(validaPerfiles(nombreTwitter, seguidoresTwitter, nombreFacebook, seguidoresFacebook, 
		        				nombreInstagram, seguidoresInstagram, nombreYoutube, seguidoresYoutube)) {
		        			Usuario u = new Usuario();
		    	        	u.setNombreCuenta(nombreCuenta);
		    	        	u.setNombre(nombre);
		    	        	u.setEdad(Integer.valueOf(edad));
		    	        	u.setPassword(Usuario.encodePassword(pass1));
		    	        	u.setRoles("Usuario,Influencer");
		    	        	u.setActivo(Byte.MAX_VALUE);
		    	        	u.setNum_contrataciones(0);
		    	        	entityManager.persist(u);
		    	        	insertaPerfiles(nombreTwitter, seguidoresTwitter, nombreFacebook, seguidoresFacebook, 
			        				nombreInstagram, seguidoresInstagram, nombreYoutube, seguidoresYoutube, u);
		    	        	entityManager.flush();
		    	        	insertaImagenUsuario(imagenPerfil, u.getId());

		    	        	
		    	        	mensaje="Usuario registrado correctamente";
		        		}
		        		else {
		        			//Mostrar mensaje de error al usuario
		                	mensaje = "Error. Perfiles de RRSS no válidos";
		        		}
	        		}
        			else {
        				mensaje="Error. Debe introducir una edad media de sus seguidores";
        			}
        		}
        		else {
        			Usuario u = new Usuario();
    	        	u.setNombreCuenta(nombreCuenta);
    	        	u.setNombre(nombre);
    	        	u.setCandidaturas(new ArrayList<>());
    	        	u.setActivo(Byte.valueOf("1"));
					u.setRoles(String.join(",", new String[]{ 
						Rol.USER.toString(), 
						Rol.EMPRESA.toString() 
					}));
    	        	u.setPassword(Usuario.encodePassword(pass1));
    	        	u.setNum_contrataciones(0);
    	        	entityManager.persist(u);
    	        	entityManager.flush();
    	        	insertaImagenUsuario(imagenPerfil, u.getId());
    	        	mensaje="Usuario registrado correctamente";
        		}
       
        		
        		
        	}
        	else
        	//Mostrar mensaje de error al usuario
        		mensaje = "Error. Las contraseñas no coinciden";
        }
		session.setAttribute("mensajeInfo", mensaje);
		try {
			response.sendRedirect("/login");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.info("Error al redireccionar");
		}

    }
    
    private void insertaImagenUsuario(MultipartFile imagenPerfil, long idUsuario) {
		// TODO Auto-generated method stub
    	
    	File f = localData.getFile("usuario", String.valueOf(idUsuario));
  		if (!imagenPerfil.isEmpty()) {
  			//Subir imagen por defecto al perfil
  			try (BufferedOutputStream stream =
  					new BufferedOutputStream(new FileOutputStream(f))) {
  				byte[] bytes = imagenPerfil.getBytes();
  				stream.write(bytes);
  			} catch (Exception e) {
  				log.warn("Error uploading " + String.valueOf(idUsuario) + " ", e);
  			}
		}
		
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
	
	
	@GetMapping(value="/{id}/photo")
	public StreamingResponseBody getPhotoUsuario(@PathVariable long id, Model model) throws IOException {		
		File f = localData.getFile("usuario", ""+id);
		InputStream in;
		if (f.exists()) {
			in = new BufferedInputStream(new FileInputStream(f));
		} else {
			in = new BufferedInputStream(getClass().getClassLoader()
					.getResourceAsStream("static/img/profile.png"));
		}
		return new StreamingResponseBody() {
			@Override
			public void writeTo(OutputStream os) throws IOException {
				FileCopyUtils.copy(in, os);
			}
		};
	}

	
	
}
