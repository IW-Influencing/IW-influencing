package es.ucm.fdi.iw.control;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import es.ucm.fdi.iw.model.Candidatura;
import es.ucm.fdi.iw.model.Evento;
import es.ucm.fdi.iw.model.Message;
import es.ucm.fdi.iw.model.Propuesta;
import es.ucm.fdi.iw.model.Usuario;

@Controller()
@RequestMapping("contrataciones")
public class ContratacionesController {
	private static final Logger log = LogManager.getLogger(ContratacionesController.class);
	@Autowired 
	private EntityManager entityManager;
	
	
	
	
	@GetMapping("")
	public String getContrataciones(Model model, HttpSession session) {
		Usuario u = (Usuario)session.getAttribute("u");
		List<Candidatura> candidaturasEnVigor;
		List<Candidatura> candidaturasPendientes;
		
		if(u.getRoles().contains("ADMIN")){
			candidaturasEnVigor = entityManager.createNamedQuery("Candidatura.getAllActive", Candidatura.class).getResultList();
	    	candidaturasPendientes = entityManager.createNamedQuery("Candidatura.getPendingCandidatures", Candidatura.class).getResultList();
		}
		else{
			candidaturasEnVigor = entityManager.createNamedQuery("Candidatura.getAllActiveById", Candidatura.class)
				.setParameter("idUsuario",((Usuario)session.getAttribute("u")).getId()).getResultList();
	    	candidaturasPendientes = entityManager.createNamedQuery("Candidatura.getPendingCandidaturesById", Candidatura.class)
	    		.setParameter("idUsuario",((Usuario)session.getAttribute("u")).getId()).getResultList();

		}

	    model.addAttribute("candidaturasEnVigor", candidaturasEnVigor);
	    model.addAttribute("candidaturasPendientes", candidaturasPendientes);
		return "contrataciones";
	}
	
	@GetMapping("/busca")
	public String postSearch(Model model, HttpSession session, @RequestParam String patron) {
		patron = "%"+patron+"%";
		List<Candidatura> candidaturas = entityManager.createNamedQuery("Candidatura.searchByName", Candidatura.class)
				.setParameter("patron", patron).setParameter("idUsuario",((Usuario)session.getAttribute("u")).getId())
				.getResultList();
		model.addAttribute("resultadoBusqueda", candidaturas);
		return "fragments/resultadoBusquedaContrataciones";
	}
	
	@GetMapping("/valorar")
	  public String valorarContratacion(Model model, HttpSession session, @RequestParam long idContratacion) {
		  Propuesta p = entityManager.find(Propuesta.class, idContratacion);
		  model.addAttribute("modo", "CREACION");
		  model.addAttribute("propuesta", p);
		  return "modals/valoracion";
	  }
	
	  @GetMapping("/vista")
	  public String vistaContratacion(Model model, HttpSession session, @RequestParam long idPropuesta) {
		  Propuesta p = entityManager.find(Propuesta.class, idPropuesta);
		  model.addAttribute("modo", "CONTRATACION");
		  model.addAttribute("propuesta", p);
		  return "modals/propuesta";
	  }

}
