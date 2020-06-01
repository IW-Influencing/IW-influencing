package es.ucm.fdi.iw.control;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
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
import es.ucm.fdi.iw.model.Denuncia;
import es.ucm.fdi.iw.model.Evento;
import es.ucm.fdi.iw.model.Propuesta;
import es.ucm.fdi.iw.model.Usuario;
import es.ucm.fdi.iw.model.Valoracion;

@Controller()
@RequestMapping("denuncia")
public class DenunciaController {
	private static final Logger log = LogManager.getLogger(DenunciaController.class);
	@Autowired 
	private EntityManager entityManager;


    @GetMapping("")
    public String devuelveModalDenuncia(Model model, HttpSession session , @RequestParam long idDenunciado, @RequestParam String ruta){
        model.addAttribute("denunciante", entityManager.find(Usuario.class, ((Usuario)session.getAttribute("u")).getId()));
        model.addAttribute("denunciado", entityManager.find(Usuario.class, idDenunciado));
        model.addAttribute("ruta", ruta);
        model.addAttribute("modo", "CREACION");
        return "modals/denuncia";
    }

	 
    @PostMapping("/registrar")
    @Transactional
	public void registraDenuncia(Model model, HttpSession session,HttpServletResponse response, 
			@RequestParam long idDenunciado, @RequestParam String descripcion, @RequestParam String titulo, @RequestParam String ruta) {

		Usuario denunciante =  entityManager.find(Usuario.class, ((Usuario)session.getAttribute("u")).getId());
        Usuario denunciado = entityManager.find(Usuario.class, idDenunciado);
		Denuncia d = new Denuncia();
        d.setDenunciado(denunciado);
        d.setDenunciante(denunciante);
        d.setDescripcion(descripcion);
        d.setTitulo(titulo);
        d.setTramitada(false);
        d.setFecha(LocalDateTime.now());
        entityManager.persist(d);
        entityManager.flush();
        System.out.println(d);
        session.setAttribute("mensajeInfo", "Denuncia enviada correctamente");
		try {
			response.sendRedirect(ruta);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.info("Error al redireccionar");
		}
	}
    
    @GetMapping("/tramitar")
    public String devuelveModalTramitarDenuncia(Model model, HttpSession session , @RequestParam long idDenuncia, @RequestParam String ruta){
        Denuncia d = entityManager.find(Denuncia.class, idDenuncia);
        model.addAttribute("denunciante", d.getDenunciante());
        model.addAttribute("denuncia", d);
        model.addAttribute("denunciado", d.getDenunciado());
        model.addAttribute("modo", "TRAMITACION");
        return "modals/denuncia";
    }
    
    @GetMapping("/ver")
    public String devuelveModalVerDenuncia(Model model, HttpSession session , @RequestParam long idDenuncia){
        Denuncia d = entityManager.find(Denuncia.class, idDenuncia);
        model.addAttribute("denunciante", d.getDenunciante());
        model.addAttribute("denuncia", d);
        model.addAttribute("denunciado", d.getDenunciado());
        model.addAttribute("modo", "VER");
        return "modals/denuncia";
    }

    @PostMapping("/tramitar")
    @Transactional
	public void tramitaDenuncia(Model model, HttpSession session,HttpServletResponse response, @RequestParam long idDenuncia, @RequestParam String ruta) {
		Denuncia d = entityManager.find(Denuncia.class, idDenuncia);
        d.setTramitada(true);
        entityManager.persist(d);
		try {
			session.setAttribute("modo", "DENUNCIAS");
	        session.setAttribute("mensajeInfo", "Denuncia tramitada");
			response.sendRedirect(ruta);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	

}
