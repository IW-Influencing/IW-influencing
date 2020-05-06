package es.ucm.fdi.iw.control;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import es.ucm.fdi.iw.LocalData;
import es.ucm.fdi.iw.model.Candidatura;
import es.ucm.fdi.iw.model.Evento;
import es.ucm.fdi.iw.model.Usuario;

/**
 * Landing-page controller
 * 
 * @author mfreire
 */
@Controller
public class RootController {

  private final int LIMITE_NOTIFICACIONES = 5;
  private final int LIMITE_MENSAJES_INICIO = 3;
  private final int LIMITE_PROPUESTAS_INICIO = 3;
	
  private static final Logger log = LogManager.getLogger(RootController.class);

 @Autowired
 private EntityManager entityManager;
 
 @Autowired
 private LocalData localData;

  @GetMapping("/")
  public String index(Model model) {
    return "login";
  }

  @GetMapping("/login")
  public String login(HttpSession session, Model model) {
	model.addAttribute("mensaje",session.getAttribute("mensajeInfo"));
	session.removeAttribute("mensajeInfo");
    return "login";
  }

  @GetMapping("/inicio")
  public String inicio(HttpSession session,Model model) {
	long idUsuario = ((Usuario)session.getAttribute("u")).getId();
	List<Evento> eventos = entityManager.createNamedQuery("Evento.getPrivateMessagesUnread").setParameter("idUsuario", idUsuario ).setMaxResults(LIMITE_MENSAJES_INICIO).getResultList();
	List<Candidatura> candidaturas = entityManager.createNamedQuery("Candidatura.getByUser").setParameter("idUsuario", idUsuario ).setMaxResults(LIMITE_PROPUESTAS_INICIO).getResultList();;
	model.addAttribute("mensajes", eventos);
	model.addAttribute("candidaturas", candidaturas);
    return "inicio";
  }
  
  @GetMapping("/valoracion")
  public String valoracion(HttpSession session, @RequestParam long idContratacion) {
    return "modals/valoracion";
  }
  
  @GetMapping("/finCampanna")
  public String finCampanna(HttpSession session) {
    return "finCampanna";
  }

  @GetMapping("/negociacion")
  public String negociacion(Model model, HttpSession session) {
	  
	  //Obtener la lista de propuestas
	  long idUsuario = ((Usuario)session.getAttribute("u")).getId();
    List<Candidatura> listaCandidatura = entityManager
      .createNamedQuery("Candidatura.activeByIdUsuario")
      .setParameter("idUsuario", idUsuario).getResultList();

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
	  model.addAttribute("nombreUsuario", ((Usuario)session.getAttribute("u")).getNombre());
	  model.addAttribute("busquedas", usuariosBuscados);
	  return "administracion";
  }

  
   
  @GetMapping("/notificaciones")
  public String notificaciones(HttpSession session, Model model) {
    List<Evento> listaNotificaciones = entityManager.createNamedQuery("Evento.adminEventsByDate").setParameter("idUsuario", ((Usuario)session.getAttribute("u")).getId()).setMaxResults(LIMITE_NOTIFICACIONES).getResultList();
	  model.addAttribute("notificaciones", listaNotificaciones);
    return "modals/notificaciones";
  }

  @GetMapping("/error")
  public String error(Model model) {
    return "error";
  }
  
  

}
