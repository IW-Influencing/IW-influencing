package es.ucm.fdi.iw.control;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.ucm.fdi.iw.model.Propuesta;

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

}
