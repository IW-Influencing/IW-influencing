package es.ucm.fdi.iw.control;

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
  public String inicio(HttpSession session) {
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
	  
	  /*Obtener la lista de propuestas
	  int idUsuario = (int) session.getAttribute("idUsuario");
	  List<Propuesta> listaPropuestas = (List<Propuesta>) entityManager.createNamedQuery("Candidatura.byCandidato").setParameter("idCandidato", idUsuario);
	  model.addAttribute("propuestas", listaPropuestas);
	   */
	  
    return "negociacion";
    
  }

  @GetMapping("/administracion")
  public String administracion(HttpSession session) {
    return "administracion";
  }

  @GetMapping("/perfil")
  public String perfil(HttpSession session) {
    return "perfil";
  }

  @GetMapping("/busquedaPerfil")
  public String busquedaPerfil(HttpSession session) {
    return "busquedaPerfil";
  }

  @GetMapping("/busquedaPropuesta")
  public String busquedaPropuesta(HttpSession session) {
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
  public String contrataciones(HttpSession session) {
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
	  
	  List<Usuario> list = usuariosService.getAllUsuarios();
	  model.addAttribute("usuarios", list);
	  return "busquedaPerfil";
  }			  
}
