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
import es.ucm.fdi.iw.model.Usuario;

@Controller()
@RequestMapping("contrataciones")
public class ContratacionesController {
	private static final Logger log = LogManager.getLogger(ContratacionesController.class);
	@Autowired 
	private EntityManager entityManager;
	
	
	
	
	@GetMapping("")
	public String getContrataciones(Model model, HttpSession session) {
		List<Candidatura> candidaturasEnVigor = entityManager.createNamedQuery("Candidatura.getAllActive", Candidatura.class)
				.setParameter("idUsuario",((Usuario)session.getAttribute("u")).getId()).getResultList();
	    List<Candidatura> candidaturasPendientes = entityManager.createNamedQuery("Candidatura.getPendingCandidatures", Candidatura.class)
	    		.setParameter("idUsuario",((Usuario)session.getAttribute("u")).getId()).getResultList();
	    model.addAttribute("candidaturasEnVigor", candidaturasEnVigor);
	    model.addAttribute("candidaturasPendientes", candidaturasPendientes);
		return "contrataciones";
	}
	
	@PostMapping("/busca")
	public String postSearch(Model model, HttpSession session, @PathVariable String patron) {
		List<Candidatura> candidaturas = entityManager.createNamedQuery("Candidatura.getSearch", Candidatura.class)
				.setParameter("idUsuario",((Usuario)session.getAttribute("u")).getId()).setParameter("patron", patron)
				.getResultList();
		model.addAttribute("candidaturasBuscadas", candidaturas);
		return "contrataciones";
	}

}
