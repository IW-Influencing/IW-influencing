package es.ucm.fdi.iw.control;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import es.ucm.fdi.iw.model.Candidatura;
import es.ucm.fdi.iw.model.Evento;
import es.ucm.fdi.iw.model.Message;
import es.ucm.fdi.iw.model.Propuesta;
import es.ucm.fdi.iw.model.Usuario;
import es.ucm.fdi.iw.services.UsuariosService;					   
/**
 * Landing-page controller
 * 
 * @author mfreire
 */
@Controller
public class RootController {

  private final int LIMITE_NOTIFICACIONES = 5;
  private final int LIMITE_MENSAJES_INICIO = 3;
	
  private static final Logger log = LogManager.getLogger(RootController.class);
  @Autowired
  UsuariosService usuariosService;	
  
 @Autowired
 private EntityManager entityManager;

  @GetMapping("/")
  public String index(Model model) {
    return "index";
  }

  @GetMapping("/inicio")
  public String inicio(HttpSession session,Model model) {
	long idUsuario = ((Usuario)session.getAttribute("u")).getId();
	List<Message> messages = entityManager.createNamedQuery("Message.getAll").setParameter("idUsuario", idUsuario ).setMaxResults(LIMITE_MENSAJES_INICIO).getResultList();
	if(messages == null) System.out.println("YES");

	model.addAttribute("mensajes", messages);
    return "inicio";
  }
  
  @GetMapping("/valoracion")
  public String valoracion(HttpSession session) {
    return "valoracion";
  }
  
  @GetMapping("/finCampanna")
  public String finCampanna(HttpSession session) {
    return "finCampanna";
  }

  @GetMapping("/negociacion")
  public String negociacion(Model model, HttpSession session) {
	  
	  //Obtener la lista de propuestas
	  long idUsuario = ((Usuario)session.getAttribute("u")).getId();
	  List<Candidatura> listaCandidatura = entityManager.createNamedQuery("Candidatura.activeByCandidato").setParameter("idCandidato", idUsuario).getResultList();
	  model.addAttribute("candidaturas", listaCandidatura);
	  
    return "negociacion";
    
  }

  @GetMapping("/administracion")
  public String administracion(Model model, HttpSession session) {
	  long idUsuario = ((Usuario)session.getAttribute("u")).getId();
	  List<Evento> listaNotificaciones = entityManager.createNamedQuery("Evento.adminEventsByDate").setParameter("idUsuario", idUsuario).setMaxResults(LIMITE_NOTIFICACIONES).getResultList();
	  model.addAttribute("notificaciones", listaNotificaciones);
	  List<Evento> ultimasBusquedas = entityManager.createNamedQuery("Evento.searchesByDate").setParameter("idUsuario", idUsuario).setMaxResults(LIMITE_NOTIFICACIONES).getResultList();
	  List<Usuario> usuariosBuscados = new ArrayList<>();
	  for (Evento e : ultimasBusquedas) {
		  for (Usuario u : ((List<Usuario>)entityManager.createNamedQuery("Usuario.byNombreUsuario").setParameter("nombre", e.getDescripcion()).getResultList())) {
			  usuariosBuscados.add(u);
		  }
	  }
	  model.addAttribute("busquedas", usuariosBuscados);
	  return "administracion";
  }

  @GetMapping("/perfil")
  public String perfil(HttpSession session) {
    return "perfil";
  }

  @GetMapping("/busquedaPropuesta")
  public String busquedaPropuesta(Model model, HttpSession session) {
    List<Propuesta> propuestas = entityManager.createNamedQuery("Propuesta.getAllProposals", Propuesta.class).getResultList();
    
	  model.addAttribute("propuestas", propuestas);
    return "busquedaPropuesta";
  }

  @GetMapping("/propuesta")
  public String propuesta(HttpSession session) {
    return "propuesta";
  }

  @GetMapping("/notificaciones")
  public String notificaciones(HttpSession session) {
    return "notificaciones";
  }

  @GetMapping("/contrataciones")
  public String contrataciones(Model model, HttpSession session) {
    List<Candidatura> candidaturasEnVigor = entityManager.createNamedQuery("Candidatura.getAllActive", Candidatura.class).getResultList();
    List<Candidatura> candidaturasPendientes = entityManager.createNamedQuery("Candidatura.getPendingCandidatures", Candidatura.class).getResultList();
    model.addAttribute("candidaturasEnVigor", candidaturasEnVigor);
    model.addAttribute("candidaturasPendientes", candidaturasPendientes);
    return "contrataciones";
    
  }


  @GetMapping("/error")
  public String error(Model model) {
    return "error";
  }
  
  @GetMapping("/edicion")
  public String edicion(Model model) {
    return "edicion";
  }
  
  //Metodos nuevos
  @GetMapping("/perfiles")
  public String busquedaPerfil(HttpSession session,Model model) {
	  
    List<Usuario> users = entityManager.createNamedQuery("Usuario.getAllUsers", Usuario.class).getResultList();
	  model.addAttribute("usuarios", users);
	  return "busquedaPerfil";
  }			
  /*
  @PostMapping("/devuelveChatNegociacion")
  public String postChat()*/
}
