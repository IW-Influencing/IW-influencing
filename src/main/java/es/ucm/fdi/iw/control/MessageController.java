package es.ucm.fdi.iw.control;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import es.ucm.fdi.iw.model.Candidatura;
import es.ucm.fdi.iw.model.Evento;
import es.ucm.fdi.iw.model.Usuario;
import es.ucm.fdi.iw.model.Evento.Tipo;

/**
 * Usuario-administration controller
 * 
 * @author mfreire
 */
@Controller()
@RequestMapping("message")
public class MessageController {

	private static final Logger log = LogManager.getLogger(MessageController.class);
	
	@Autowired 
	private EntityManager entityManager;
		
	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	@GetMapping("/")
	public String getMessages(Model model, HttpSession session) {
		return "messages";
	}


	
	
	
	@GetMapping(path = "/getChat", produces = "application/json")
	@Transactional // para no recibir resultados inconsistentes
	@ResponseBody // para indicar que no devuelve vista, sino un objeto (jsonizado)
	
	public List<Evento.TransferChat> devuelveChat(HttpSession session, @RequestParam long idCandidatura) {
		
		long userId = ((Usuario)session.getAttribute("u")).getId();
		List<Evento> mensajes = entityManager.createNamedQuery("Evento.getChat").setParameter("idCandidatura", idCandidatura).getResultList();
		Usuario u = entityManager.find(Usuario.class, userId);
		return Evento.asTransferObjects(mensajes, u);	
	}	
	
	
	
	@GetMapping(path = "/insertaMsg", produces = "application/json")
	@Transactional // para no recibir resultados inconsistentes
	@ResponseBody // para indicar que no devuelve vista, sino un objeto (jsonizado)
	
	public List<Evento.TransferChat> enviaMensaje(HttpSession session, @RequestParam long idCandidatura, @RequestParam String msg, @RequestParam long idReceptor) {
		
		Evento e = new Evento();
		e.setDescripcion(msg);
		e.setCandidatura(entityManager.find(Candidatura.class, idCandidatura));
		e.setEmisor(entityManager.find(Usuario.class, ((Usuario)session.getAttribute("u")).getId()));
		e.setFechaEnviado(LocalDateTime.now());
		e.setLeido(false);
		e.setTipo(Tipo.CHAT);
		e.setReceptor(entityManager.find(Usuario.class, idReceptor));
		entityManager.persist(e);

		List<Evento.TransferChat> chatActualizado = devuelveChat(session, idCandidatura);

		// y ahora, también lo enviamos por WS
		messagingTemplate.convertAndSend(
			"/user/a/queue/updates", 
			chatActualizado);
		messagingTemplate.convertAndSend(
			"/user/"+e.getReceptor().getNombreCuenta()+"/queue/updates", 
			chatActualizado);
		log.info("Enviado mensaje via WS a {}", e.getReceptor().getNombreCuenta());
		
		return chatActualizado;
	}	
	

	@GetMapping(path = "/unread", produces = "application/json")
	@ResponseBody
	public String checkUnread(HttpSession session) {
		long UsuarioId = ((Usuario)session.getAttribute("u")).getId();		
		long unread = entityManager.createNamedQuery("Message.countUnread", Long.class)
			.setParameter("UsuarioId", UsuarioId)
			.getSingleResult();
		session.setAttribute("unread", unread);
		return "{\"unread\": " + unread + "}";
	}
}
