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
import es.ucm.fdi.iw.model.Denuncia;
import es.ucm.fdi.iw.model.Propuesta;
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
		if (target.getActivo() == true) {
			// disable
			File f = localData.getFile("Usuario", ""+id);
			if (f.exists()) {
				f.delete();
			}
			// disable Usuario
			target.setActivo(false); 
		} else {
			// enable Usuario
			target.setActivo(true);
		}
		return index(model);
	}


	@GetMapping("/influencers")
	public String searchInfluencers(Model model){
		model.addAttribute("modo", "INFLUENCER");
		model.addAttribute("resultado", entityManager.createNamedQuery("Usuario.getAllInfluencers", Usuario.class).getResultList());
		return "fragments/tablaAdmin";
	}


	@GetMapping("/empresas")
	public String searchEmpresas(Model model){
		model.addAttribute("modo", "EMPRESA");
		model.addAttribute("resultado", entityManager.createNamedQuery("Usuario.getAllEmpresas", Usuario.class).getResultList());
		return "fragments/tablaAdmin";
	}


	@GetMapping("/denuncias")
	public String searchDenuncias(Model model){
		model.addAttribute("modo", "DENUNCIA");
		model.addAttribute("resultado", entityManager.createNamedQuery("Denuncia.getAllDenuncias", Denuncia.class).getResultList());
		return "fragments/tablaAdmin";
	}


	@GetMapping("/propuestas")	
	public String searchPropuestas(Model model){
		model.addAttribute("modo", "PROPUESTA");
		model.addAttribute("resultado", entityManager.createNamedQuery("Propuesta.getAllProposals", Propuesta.class).getResultList());
		return "fragments/tablaAdmin";
	}

	@GetMapping("/eliminaInfluencer")
	@Transactional
	public String eliminaInfluencer(Model model,  @RequestParam long id){
		Usuario u = entityManager.find(Usuario.class, id);
		u.setActivo(false);
		entityManager.persist(u);
		return searchInfluencers(model);
	}


	@GetMapping("/eliminaEmpresa")
	@Transactional
	public String eliminaEmpresa(Model model,  @RequestParam long id){
		Usuario u = entityManager.find(Usuario.class, id);
		u.setActivo(false);
		entityManager.persist(u);
		return searchEmpresas(model);
	}

	@GetMapping("/eliminaPropuesta")	
	@Transactional
	public String eliminaPropuesta(Model model,  @RequestParam long id){
		Propuesta p = entityManager.find(Propuesta.class, id);
		p.setActiva(false);
		entityManager.persist(p);
		return searchPropuestas(model);
	}

	@GetMapping("/verificaInfluencer")
	@Transactional
	public String verificaInfluencer(Model model,  @RequestParam long id){
		Usuario u = entityManager.find(Usuario.class, id);
		u.setVerificado(true);
		entityManager.persist(u);
		return searchInfluencers(model);
	}


	@GetMapping("/verificaEmpresa")
	@Transactional
	public String verificaEmpresa(Model model,  @RequestParam long id){
		Usuario u = entityManager.find(Usuario.class, id);
		u.setVerificado(true);
		entityManager.persist(u);
		return searchEmpresas(model);
	}

	@GetMapping("/verificaPropuesta")
	@Transactional
	public String verificaPropuesta(Model model,  @RequestParam long id){
		Propuesta p = entityManager.find(Propuesta.class, id);
		p.setVerificado(true);
		entityManager.persist(p);
		return searchPropuestas(model);
	}
}
