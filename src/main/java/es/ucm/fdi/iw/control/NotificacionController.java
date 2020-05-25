package es.ucm.fdi.iw.control;

import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import es.ucm.fdi.iw.model.Evento;


@Controller()
@RequestMapping("notificaciones")
public class NotificacionController {

	 @Autowired
	 private EntityManager entityManager;
	 
	  @GetMapping("")
	  public String notificaciones(HttpSession session, Model model) {
	    List<Evento> listaNotificaciones = entityManager.createNamedQuery("Evento.adminEventsByDate").getResultList();
		model.addAttribute("notificaciones", listaNotificaciones);
	    return "modals/notificaciones";
	  
	  }
	  
	  @GetMapping("/elimina")
	  @Transactional
	 @ResponseBody
	  public String eliminaNotificacion(@RequestParam long idNotificacion) {
	    Evento notificacion = entityManager.find(Evento.class, idNotificacion);
	    notificacion.setLeido(true);
	    entityManager.persist(notificacion);
	    List<Evento> eventos = entityManager.createNamedQuery("Evento.adminEventsByDate").getResultList();
	    return String.valueOf(entityManager.createNamedQuery("Evento.adminEventsByDate").getResultList().size());
	  }
	  
}
