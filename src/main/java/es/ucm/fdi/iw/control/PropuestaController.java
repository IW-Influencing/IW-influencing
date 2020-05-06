package es.ucm.fdi.iw.control;

import java.time.LocalDateTime;
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
	public String solicitaPropuesta(Model model, HttpSession session, @RequestParam long idPropuesta){
		  
		  String mensaje = "";
		  //Comprobar que el usuario no esté apuntado previamente
		  if (entityManager.createNamedQuery("Candidatura.byCandidato").setParameter("idCandidato", ((Usuario)session.getAttribute("u")).getId()).getResultList().isEmpty()) {
			  Usuario usuarioLoggeado = entityManager.find(Usuario.class, ((Usuario)session.getAttribute("u")).getId());
			  int edad = usuarioLoggeado.getEdad();
			  Propuesta p = entityManager.find(Propuesta.class, idPropuesta);
			  if (p.getEdadMinPublico() > edad || p.getEdadMaxPublico() < edad) {
				  mensaje="La edad de su público no corresponde con la propuesta";
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
			  }
			  
		  }
		  else {
			  mensaje = "Ya estaba apuntado a esta propuesta";
		  }
				 

		  
		  return "redirect:busquedaPropuesta";
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

}
