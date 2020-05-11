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
import es.ucm.fdi.iw.model.Propuesta;
import es.ucm.fdi.iw.model.Usuario;

@Controller()
@RequestMapping("contrataciones")
public class ContratacionesController {
	private static final Logger log = LogManager.getLogger(ContratacionesController.class);
	@Autowired 
	private EntityManager entityManager;
	private final int NUM_ELEMENTOS_PAGINA = 3;
	
	
	
	
	@GetMapping("")
	public String getContrataciones(Model model, HttpSession session) {
		Usuario u = (Usuario)session.getAttribute("u");
		List<Candidatura> candidaturas;
		candidaturas = entityManager.createNamedQuery("Candidatura.getByUser", Candidatura.class)					
				.setParameter("idUsuario",((Usuario)session.getAttribute("u")).getId()).getResultList();
		model.addAttribute("numeroPaginas", Math.ceil((double)candidaturas.size()/NUM_ELEMENTOS_PAGINA));
		if (NUM_ELEMENTOS_PAGINA <= candidaturas.size())
			candidaturas=candidaturas.subList(0,NUM_ELEMENTOS_PAGINA);
		model.addAttribute("modo", "Contrataciones");
	    model.addAttribute("resultadoBusqueda", candidaturas);
		return "contrataciones";
	}
	
	
	   @GetMapping("/busca")
		public String postSearch(Model model, HttpSession session,@RequestParam(required = true, defaultValue = "1") int indicePagina, @RequestParam String patron) {
		    String patronParaLike = "%"+patron+"%";
			List<Candidatura> candidaturas = null;
			if (patron.isEmpty()) { 
				candidaturas = entityManager.createNamedQuery("Candidatura.getByUser", Candidatura.class)					
						.setParameter("idUsuario",((Usuario)session.getAttribute("u")).getId()).getResultList();
			} else {
				candidaturas = entityManager.createNamedQuery("Candidatura.searchByName", Candidatura.class)
						.setParameter("patron", patron).setParameter("idUsuario",((Usuario)session.getAttribute("u")).getId())
						.getResultList();		
			}
			
			model.addAttribute("numeroPaginas", Math.ceil((double)candidaturas.size()/NUM_ELEMENTOS_PAGINA));
			if (indicePagina*NUM_ELEMENTOS_PAGINA <= candidaturas.size())
				candidaturas=candidaturas.subList((indicePagina-1)*NUM_ELEMENTOS_PAGINA, indicePagina*NUM_ELEMENTOS_PAGINA);
			else 
				candidaturas=candidaturas.subList((indicePagina-1)*NUM_ELEMENTOS_PAGINA, candidaturas.size());

			model.addAttribute("modo", "Resultados de la búsqueda");
			model.addAttribute("patron", patron);
			model.addAttribute("resultadoBusqueda", candidaturas);
			return "fragments/resultadoBusquedaContrataciones";
		}
	    
	    @GetMapping("/estado")
		public String postSearchByTag(Model model, HttpSession session, @RequestParam(required = true, defaultValue = "1") int indicePagina, @RequestParam String estado ) {
			String estadoLike;
			if (estado.equals(" ALL"))
				estadoLike = "%%";
			else
				estadoLike = "%"+estado+"%";
			List<Candidatura> candidaturas = null;
			if (estado.isEmpty()) { 
				candidaturas = entityManager.createQuery("select c from Candidatura c", Candidatura.class).getResultList();
			} else {
				candidaturas = entityManager.createNamedQuery("Candidatura.searchByEstado", Candidatura.class)
						.setParameter("estado", estadoLike).setParameter("idUsuario",((Usuario)session.getAttribute("u")).getId()).getResultList();			
			}
			model.addAttribute("numeroPaginas", Math.ceil((double)candidaturas.size()/NUM_ELEMENTOS_PAGINA));
			if (indicePagina*NUM_ELEMENTOS_PAGINA <= candidaturas.size())
				candidaturas=candidaturas.subList((indicePagina-1)*NUM_ELEMENTOS_PAGINA, indicePagina*NUM_ELEMENTOS_PAGINA);
			else 
				candidaturas=candidaturas.subList((indicePagina-1)*NUM_ELEMENTOS_PAGINA, candidaturas.size());

			model.addAttribute("modo", "Contrataciones "+ estado);

			model.addAttribute("resultadoBusqueda", candidaturas);
			model.addAttribute("patron", estado);
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
