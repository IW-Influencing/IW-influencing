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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import es.ucm.fdi.iw.model.Candidatura;
import es.ucm.fdi.iw.model.PerfilRRSS;
import es.ucm.fdi.iw.model.Usuario;

@Controller()
@RequestMapping("perfil")
public class PerfilController {

    @Autowired
    private EntityManager entityManager;

	private static final Logger log = LogManager.getLogger(PerfilController.class);

    
    @GetMapping("")
    public String perfil(Model model, HttpSession session, @RequestParam long idUsuario) {
        Usuario u = entityManager.find(Usuario.class, idUsuario);
        List<PerfilRRSS>perfiles = entityManager.createNamedQuery("PerfilRRSS.byInfluencer", PerfilRRSS.class)
        		.setParameter("idUsuario",idUsuario).getResultList();
        
        model.addAttribute("modo", "VISTA");
        model.addAttribute("usuario", u);
        model.addAttribute("perfilesRRSS",perfiles); 		
        		
        return "modals/perfil";
    }

    @GetMapping("/edicion")
    public String edicionPerfil(Model model, HttpSession session) {
        Usuario u = entityManager.find(Usuario.class, ((Usuario) session.getAttribute("u")).getId());
        model.addAttribute("modo", "EDICION");
        model.addAttribute("usuario", u);
        return "modals/perfil";
    }

    @GetMapping("/creacion")
    public String creacion(Model model, HttpSession session) {
        model.addAttribute("modo", "CREACION");
        model.addAttribute("usuario",null);
        return "modals/perfil";
    }
    
    @PostMapping("/registra")
    @Transactional
    public void registraUsuario(@RequestParam("nombreCuenta") String nombreCuenta, @RequestParam("nombre") String nombre,
    		@RequestParam("pass1") String password,@RequestParam("pass2") String password2, @RequestParam("imagenPerfil") MultipartFile photo,
    		@RequestParam("nombreTwitter") String perfilTwitter, @RequestParam("seguidoresTwitter") int numSeguidoresTwitter,
    		@RequestParam("nombreFacebook") String perfilFacebook, @RequestParam("seguidoresFacebook") int numSeguidoresFacebook,
    		@RequestParam("nombreInstagram") String perfilInstagram, @RequestParam("seguidoresInstagram") int numSeguidoresInstagram,
    		@RequestParam("nombreYoutube") String perfilYoutube, @RequestParam("seguidoresYoutube") int numSeguidoresYoutube){
    	
    	/*
    	 * Validar campos
    	 * Comprobar en los Object que perfilX.nombre != null y numSeguidores >= 0
    	 * Insertar en BBDD
    	 * */
    	
		log.info("Registrando el usuario {} ", nombreCuenta);
    	if (nombreCuenta!=null) {
    		System.out.println("ggggg");
    	}
    }

}
