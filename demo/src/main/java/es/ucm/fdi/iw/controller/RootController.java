package es.ucm.fdi.iw.controller;

import java.util.Random;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RootController {

    private static Logger log = LogManager.getLogger(RootController.class);
    private static final String OBJETIVO = "o";
    private static final String INTENTOS = "i";
    private static final String RESULTADO = "resultado";
    private final Random random = new Random();
    
    
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
    
    @GetMapping("/admin")            
    public String admin(
    		HttpSession session) {
        return "admin";
    }
    
    @GetMapping("/perfil")            
    public String perfil(
    		HttpSession session) {
        return "perfil";
    }

    @GetMapping("/search_perfil")            
    public String search_perfil(
    		HttpSession session) {
        return "profileSearch";
    }

    @GetMapping("/search_propuesta")            
    public String search_propuesta(
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
}
