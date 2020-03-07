package es.ucm.fdi.iw.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * Landing-page controller
 * 
 * @author mfreire
 */
@Controller
public class RootController {
	
	private static final Logger log = LogManager.getLogger(RootController.class);

	@GetMapping("/")
	public String index(Model model) {
		return "index";
	}

 @GetMapping("/inicio")            
    public String inicio(
    		HttpSession session) {
        return "inicio";
    }
    
    @GetMapping("/negociacion")            
    public String negociacion(
    		HttpSession session) {
        return "negociacion";
    }
    
    @GetMapping("/administracion")            
    public String administracion(
    		HttpSession session) {
        return "administracion";
    }
    
    @GetMapping("/perfil")            
    public String perfil(
    		HttpSession session) {
        return "perfil";
    }

    @GetMapping("/busquedaPerfil")            
    public String busquedaPerfil(
    		HttpSession session) {
        return "busquedaPerfil";
    }

    @GetMapping("/busquedaPropuesta")            
    public String busquedaPropuesta(
    		HttpSession session) {
        return "busquedaPropuesta";
    }

    @GetMapping("/propuesta")            
    public String propuesta(
    		HttpSession session) {
        return "propuesta";
    }

    @GetMapping("/notificaciones")            
    public String notificaciones(
    		HttpSession session) {
        return "notificaciones";
    }

    @GetMapping("/contrataciones")            
    public String contrataciones(
    		HttpSession session) {
        return "contrataciones";
    }


	@GetMapping("/chat")
	public String chat(Model model, HttpServletRequest request) {
		return "chat";
	}
	
	@GetMapping("/error")
	public String error(Model model) {
		return "error";
	}	
}
