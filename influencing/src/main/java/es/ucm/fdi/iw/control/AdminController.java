package es.ucm.fdi.iw.control;

import java.io.File;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.ucm.fdi.iw.LocalData;
import es.ucm.fdi.iw.model.Usuario;

/**
 * Admin-only controller
 * @author mfreire
 */
@Controller()
@RequestMapping("admin")
public class AdminController {
	
	private static final Logger log = LogManager.getLogger(AdminController.class);
	
	@Autowired 
	private EntityManager entityManager;
	
	@Autowired
	private LocalData localData;
	
	@Autowired
	private Environment env;
	
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("activeProfiles", env.getActiveProfiles());
		model.addAttribute("basePath", env.getProperty("es.ucm.fdi.base-path"));

		model.addAttribute("Usuarios", entityManager.createQuery(
				"SELECT u FROM Usuario u").getResultList());
		
		return "admin";
	}
	
	@PostMapping("/toggleUsuario")
	@Transactional
	public String delUsuario(Model model,	@RequestParam long id) {
		Usuario target = entityManager.find(Usuario.class, id);
		if (target.getActivo() == 1) {
			// disable
			File f = localData.getFile("Usuario", ""+id);
			if (f.exists()) {
				f.delete();
			}
			// disable Usuario
			target.setActivo((byte)0); 
		} else {
			// enable Usuario
			target.setActivo((byte)1);
		}
		return index(model);
	}	
}
