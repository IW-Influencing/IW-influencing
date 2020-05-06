package es.ucm.fdi.iw.control;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
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
import es.ucm.fdi.iw.model.Candidatura;
import es.ucm.fdi.iw.model.Evento;
import es.ucm.fdi.iw.model.Propuesta;
import es.ucm.fdi.iw.model.Usuario;
import es.ucm.fdi.iw.model.Evento.Tipo;
import es.ucm.fdi.iw.model.Usuario.Rol;

@Controller()
@RequestMapping("propuesta")
public class PropuestaController {
	
	private static final Logger log = LogManager.getLogger(PropuestaController.class);
	
	@Autowired
	private LocalData localData;
	
	@Autowired 
	private EntityManager entityManager;
	
	  @GetMapping("")
	  public String propuesta(Model model, HttpSession session, @RequestParam long idPropuesta) {
		  Propuesta p = entityManager.find(Propuesta.class, idPropuesta);
		  model.addAttribute("propuesta", p);
		  model.addAttribute("modo", "VISTA");
		  return "modals/propuesta";
	  }
	  
	  @GetMapping("/creacion")
	  public String creacion(Model model, HttpSession session) {
		  model.addAttribute("modo","CREACION");
		  return "modals/propuesta";
	  }
	  
	  @GetMapping("/vistaUltimatum")
	  public String vistaUltimatum(Model model, HttpSession session, @RequestParam long idPropuesta) {
		  Propuesta p = entityManager.find(Propuesta.class, idPropuesta);
		  model.addAttribute("modo", "VISTA-ULTIMATUM");
		  model.addAttribute("propuesta", p);
		  return "modals/propuesta";
	  }
	    
	  @GetMapping("/ultimatum")
	  public String ultimatum(Model model, HttpSession session, @RequestParam long idPropuesta) {
		  Propuesta p = entityManager.find(Propuesta.class, idPropuesta);
		  model.addAttribute("modo", "ULTIMATUM");
		  model.addAttribute("propuesta", p);
		  return "modals/propuesta";
	  }
	  
	  
	  //Ruta que maneja cuando un usuario se apunta a una propuesta. Tiene que crear una candidatura
	  @PostMapping("/solicitaPropuesta")
	  @Transactional
	public void solicitaPropuesta(Model model, HttpSession session,HttpServletResponse response,  @RequestParam long idPropuesta){
		  
		  String mensaje = "";
		  //Comprobar que el usuario no esté apuntado previamente
		  if (entityManager.createNamedQuery("Candidatura.byCandidatoAndPropuesta").setParameter("idCandidato", ((Usuario)session.getAttribute("u")).getId()).setParameter("idPropuesta", idPropuesta).getResultList().isEmpty()) {
			  Usuario usuarioLoggeado = entityManager.find(Usuario.class, ((Usuario)session.getAttribute("u")).getId());
			  int edad = usuarioLoggeado.getEdad();
			  Propuesta p = entityManager.find(Propuesta.class, idPropuesta);
			  if (p.getEdadMinPublico() > edad || p.getEdadMaxPublico() < edad) {
				  mensaje="La edad de tu público no corresponde con la propuesta";
			  }
			  else {
				  Candidatura c = new Candidatura();
				  c.setAceptada(false);
				  c.setCandidato(usuarioLoggeado);
				  c.setEstado(Candidatura.Estado.NEGOCIANDO.toString());
				  c.setPropuesta(p);
				  entityManager.persist(c);
				  //Enviar mensaje de usuario se ha apuntado a la propuesta como emisor el usuario y receptor la empresa
				   
				  	Evento e = new Evento();
					e.setDescripcion("El usuario " + usuarioLoggeado.getNombre() + " se ha apuntado a la propuesta");
					e.setCandidatura(c);
					e.setEmisor(usuarioLoggeado);
					e.setFechaEnviado(LocalDateTime.now());
					e.setLeido(false);
					e.setTipo(Tipo.CHAT);
					e.setReceptor(p.getEmpresa());
					entityManager.persist(e);
					mensaje = "Te has apuntado correctamente a la propuesta";

			  }
			  
			  
			  
		  }
		  else {
			  mensaje = "Ya estabas apuntado a esta propuesta";
		  }
				 

		  try {
				session.setAttribute("mensajeInfo", mensaje);
				response.sendRedirect("/busquedaPropuesta");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.info("Error al redireccionar");
		}
	  }
	  
	  
	  //Ruta que maneja cuando un usuario acepta/rechaza ultimatum. Tiene que aceptar/rechazar candidatura
	  @PostMapping("/decideUltimatum")
	    @Transactional
		public String decideUltimatum(RedirectAttributes redirectAttributes, Model model, @RequestParam String nombreCuenta, 
				@RequestParam String nombre,
	    		@RequestParam String pass1,@RequestParam String pass2, 
	    		@RequestParam MultipartFile imagenPerfil, @RequestParam String tipoCuenta, 
	    		@RequestParam String nombreTwitter, @RequestParam String seguidoresTwitter,
	    		@RequestParam String nombreFacebook, @RequestParam String seguidoresFacebook,
	    		@RequestParam String nombreInstagram, @RequestParam String seguidoresInstagram,
	    		@RequestParam String nombreYoutube, @RequestParam String seguidoresYoutube){

	    	String mensaje = "";
	        
	        return "redirect:login";
	    }
	  
	  
	  //Manejador para cuando se manda un ultimatum al otro usuario
	  //Tiene que registrar la propuesta con los datos y añadir un mensaje para enviarle el ultimatum.
	  @PostMapping("/enviaUltimatum")
	    @Transactional
		public String enviaUltimatum(RedirectAttributes redirectAttributes, Model model, @RequestParam String nombreCuenta, 
				@RequestParam String nombre,
	    		@RequestParam String pass1,@RequestParam String pass2, 
	    		@RequestParam MultipartFile imagenPerfil, @RequestParam String tipoCuenta, 
	    		@RequestParam String nombreTwitter, @RequestParam String seguidoresTwitter,
	    		@RequestParam String nombreFacebook, @RequestParam String seguidoresFacebook,
	    		@RequestParam String nombreInstagram, @RequestParam String seguidoresInstagram,
	    		@RequestParam String nombreYoutube, @RequestParam String seguidoresYoutube){

	    	String mensaje = "";
	        
	        return "redirect:login";
	    }
	  
	  //Manejador para cuando se crea una propuesta
	  //Tiene que registrar la propuesta en la BDD
	  @PostMapping("/registraPropuesta")
	    @Transactional
		public String registraPropuesta(RedirectAttributes redirectAttributes, Model model, 
				@RequestParam String nombre, @RequestParam String descripcion,
	    		@RequestParam String edades,@RequestParam String fechaInicio, @RequestParam String fechaFin, 
	    		@RequestParam MultipartFile imagenPropuesta, @RequestParam String tags, @RequestParam long idPropuesta){

	        return "redirect:login";
	    }
	  
	  @GetMapping(value="/{id}/photo")
		public StreamingResponseBody getPhotoUsuario(@PathVariable long id, Model model) throws IOException {		
			File f = localData.getFile("propuesta", ""+id);
			InputStream in;
			if (f.exists()) {
				in = new BufferedInputStream(new FileInputStream(f));
			} else {
				in = new BufferedInputStream(getClass().getClassLoader()
						.getResourceAsStream("static/img/propuesta.png"));
			}
			return new StreamingResponseBody() {
				@Override
				public void writeTo(OutputStream os) throws IOException {
					FileCopyUtils.copy(in, os);
				}
			};
		}


}
