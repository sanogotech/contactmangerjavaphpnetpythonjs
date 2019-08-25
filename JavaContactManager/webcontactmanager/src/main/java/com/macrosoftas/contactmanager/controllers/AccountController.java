package com.macrosoftas.contactmanager.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.macrosoftas.contactmanager.entities.User;
import com.macrosoftas.contactmanager.forms.LoginForm;
import com.macrosoftas.contactmanager.services.LoginService;
import com.macrosoftas.contactmanager.services.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.util.List;


@Controller
@RequestMapping(value = "/CONTACTMANAGER")
public class AccountController {

	private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
	
	@Autowired
    private LoginService loginService;

	/**
	 * Chargement du formulaire de connexion
	 * 	
	 * @param model
	 * @return
	 */
    @RequestMapping(value= {"/","/user/login"},  method = RequestMethod.GET)
    public String showLoginForm(Model model) {
    	model.addAttribute("loginForm", new LoginForm());
		model.addAttribute("active_menu", "login");
        return "user/login";
    }
    
    /**
	 * Connexion de l'utilisateur
	 * 	
	 * @param username
	 * @param password
	 * @param model
	 * @return
	 */
    @RequestMapping(value = "/user/login",
            method = RequestMethod.POST)
    public String showLoginForm(
            @Valid LoginForm loginForm,
            BindingResult bindingResult,
            HttpSession session,
            Model model) {
        
        logger.debug("Connexion utilisateur => " + loginForm);
		 
		 //si erreur on fais passer ces erreurs à la vue
	        if (bindingResult.hasErrors()) {
	            logger.warn("Il y a des erreurs de validation");
	            List<ObjectError> allErrors = bindingResult.getAllErrors();
	            for (ObjectError oe : allErrors) {
	                logger.debug("Error => " + oe.getDefaultMessage());
	            }
	            
	            model.addAttribute("error", allErrors);
	            return "user/login";
	        }
        
        if (! loginService.authentice(loginForm.getUsername(),
                loginForm.getPassword())) {
        	 logger.error("Le nom d'utilisateur ou le mot de passe est incorrect. Vérifiez à nouveau");
        	 model.addAttribute("error", "Le nom d'utilisateur ou le mot de passe est incorrect. Vérifiez à nouveau");
            return "user/login";
        }

        // Connexion réussie, on le rédirige à la page d'accueil
        logger.info("Connexion réussie");
        /*--------------------------Création des varaiables de session----------------------------------------*/
        System.out.println(loginForm.getUsername());
        
        model.addAttribute("sessionId", session.getId() ); //  Session actuelle et faire passer à la vue
		model.addAttribute("sessionNew", session.isNew()); //Une nouvelle session et faire passer à la vue
		
		session.setAttribute("username", loginForm.getUsername());
		session.setAttribute("isLoggin", true);		
		session.setAttribute("user",loginForm); //on crée une variable de session de type objet loginForm
		//ex recup
		//session.loginForm.username;		
		
		/*User user = userService.findAllByUsername(loginForm.getUsername());
		System.out.println(user);
		if(user != null) {
			model.addAttribute("sessionUsername", user.getUsername()); //
			model.addAttribute("sessionFullname", user.getFirstname() + " "+user.getLastname()); //
			model.addAttribute("sessionIsLoggin", true); //
		}*/
		model.addAttribute("active_menu", "login");
        model.addAttribute("success", "Connexion réussie");
        return "redirect:/";
    }
    
    /**
	 * Déconnexion de l'utilisateur
	 * 	
	 * @param model
	 * @return
	 */
    @RequestMapping(value="/user/logout",  method = RequestMethod.GET)
    public String deconnectLoginForm( HttpSession session, Model model) {
    	model.addAttribute("loginForm", new LoginForm());
		model.addAttribute("active_menu", "login");
		model.addAttribute("success", "Vous êtes deconnecté");
		// on detruit la session
		if(session != null) session.invalidate();
        return "user/login";
    }
}
