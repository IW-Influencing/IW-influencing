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
import jdk.internal.jline.internal.Log;

@Controller()
@RequestMapping("denuncia")
public class DenunciaController {
	private static final Logger log = LogManager.getLogger(DenunciaController.class);
	@Autowired 
	private EntityManager entityManager;


    @GetMapping("")
    public String devuelveModalDenuncia(Model model, HttpSession session , @RequestParam long idDenunciado){
        Usuario denunciado = entityManager.find(Usuario.class, idDenunciado);
        model.addAttribute("denunciante", entityManager.find(Usuario.class, ((Usuario)session.getAttribute("u")).getId()));
        model.addAttribute("denunciado", entityManager.find(Usuario.class, idDenunciado));
        model.addAttribute("modo", "CREACION");
        return "denuncia";
    }

	 
    @PostMapping("/registrar")
    @Transactional
	public void registraDenuncia(Model model, HttpSession session, @RequestParam long idDenunciado, @RequestParam String descripcion, @RequestParam String titulo) {

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
	}

     @PostMapping("/tramitar")
     @Transactional
	public void tramitaDenuncia(Model model, HttpSession session, @RequestParam long idDenuncia) {

		Denuncia d = entityManager.find(Denuncia.class, idDenuncia);
        d.setTramitada(true);
        entityManager.persist(d);

	}
	

}
