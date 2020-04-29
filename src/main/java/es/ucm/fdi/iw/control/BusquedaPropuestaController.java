package es.ucm.fdi.iw.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.ucm.fdi.iw.model.Propuesta;
import es.ucm.fdi.iw.model.Usuario;

import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Controller()
@RequestMapping("busquedaPropuesta")
public class BusquedaPropuestaController {
    
    private static final Logger log = LogManager.getLogger(BusquedaPropuestaController.class);
    
    @Autowired 
    private EntityManager entityManager;
	private final int NUM_ELEMENTOS_PAGINA=2;

    
    @GetMapping("")
	public String getPropuestas(Model model, HttpSession session) {
		List<Propuesta> propuestas = entityManager.createNamedQuery("Propuesta.getAllProposals", Propuesta.class).getResultList();
		model.addAttribute("numeroPaginas", Math.ceil((double)propuestas.size()/NUM_ELEMENTOS_PAGINA));
		propuestas=propuestas.subList(0,NUM_ELEMENTOS_PAGINA);
	    model.addAttribute("propuestas", propuestas);
        return "busquedaPropuesta";
    }
    
    
    @GetMapping("/busca")
	public String postSearch(Model model, HttpSession session,@RequestParam(required = true, defaultValue = "1") int indicePagina, @RequestParam String patron) {
		String patronParaLike = "%"+patron+"%";
		List<Propuesta> propuestas = null;
		if (patron.isEmpty()) { 
			propuestas = entityManager.createQuery("select p from Propuesta p", Propuesta.class).getResultList();
		} else {
			propuestas = entityManager.createNamedQuery("Propuesta.searchByNombre", Propuesta.class)
					.setParameter("nombre", patron).getResultList();		
		}
		
		model.addAttribute("numeroPaginas", Math.ceil((double)propuestas.size()/NUM_ELEMENTOS_PAGINA));
		if (indicePagina*NUM_ELEMENTOS_PAGINA <= propuestas.size())
			propuestas=propuestas.subList((indicePagina-1)*NUM_ELEMENTOS_PAGINA, indicePagina*NUM_ELEMENTOS_PAGINA);
		else 
			propuestas=propuestas.subList((indicePagina-1)*NUM_ELEMENTOS_PAGINA, propuestas.size());

		
		
		model.addAttribute("patron", patron);
		model.addAttribute("resultadoBusqueda", propuestas);
		return "fragments/resultadoBusquedaPropuestas";
	}
    
    
  
    @GetMapping("/propuesta")
    public String propuesta(Model model, HttpSession session, @RequestParam long idPropuesta) {
        Propuesta p = entityManager.find(Propuesta.class, idPropuesta);
        model.addAttribute("propuesta", p);
        return "modals/propuesta";
    } // copiado

}
