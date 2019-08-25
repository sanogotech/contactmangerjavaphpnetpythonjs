package com.macrosoftas.contactmanager.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.macrosoftas.contactmanager.entities.User;
import com.macrosoftas.contactmanager.services.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User controller.
 */
@Controller
@RequestMapping(value = "/CONTACTMANAGER")
public class UserController {

	private UserService userService;
	private String cmd="";
	private String msg_error;
	private String msg_success;
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * Liste tous les users.
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public String list(Model model) {
		model.addAttribute("users", userService.listAllUsers());
		model.addAttribute("active_menu", "listuser");	
		model.addAttribute("error", msg_error);
		model.addAttribute("success", msg_success);
		return "user/list";
	}

	/**
	 * Afficher un user spécifique par son identifiant.
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("user/{id}")
	public String showUser(@PathVariable Long id, Model model) {
		model.addAttribute("user", userService.getUserById(id));
		model.addAttribute("active_menu", "listuser");
		model.addAttribute("error", msg_error);
		model.addAttribute("success", msg_success);
		return "user/show";
	}

	// Afficher le formulaire de modification du User
	@RequestMapping("user/edit/{id}")
	public String edit(@PathVariable Long id, Model model) {
		model.addAttribute("user", userService.getUserById(id));
		model.addAttribute("active_menu", "listuser");
		model.addAttribute("cmd", "edit");
		cmd = "edit";
		return "user/register";
	}

	/**
	 * Nouveau user.
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("user/signup")
	public String newUser(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("active_menu", "signup");
		model.addAttribute("cmd", "new");
		cmd = "new";
		return "user/register";
	}

	/**
	 * Enregistrer le user dans la base de données.
	 *
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "user", method = RequestMethod.POST)
	public String saveUser(@Valid User user, BindingResult bindingResult, Model model) {
		
		 logger.debug("dans user => " + user);
		 
		 //si erreur on fais passer ces erreurs à la vue
	        if (bindingResult.hasErrors()) {
	            logger.warn("Il y a des erreurs de validation");
	            List<ObjectError> allErrors = bindingResult.getAllErrors();
	            for (ObjectError oe : allErrors) {
	                logger.debug("Error => " + oe.getDefaultMessage());
	            }
	            
	            model.addAttribute("cmd", cmd);
	            return "user/register";
	        }

	User currentUser	= userService.saveUser(user);
		
	if (currentUser != null) {
		if(cmd == "new")
			msg_success = "Un nouveau user "+user.getFirstname()+" "+user.getLastname()+" a été créé avec succès.";
		else
			msg_success = "Le user a été mis à jour avec succès.";		
	}
	else {
		if(cmd == "new")
			msg_error = "La création du nouveau user "+user.getFirstname()+" "+user.getLastname()+" a échoué.";
		else
			msg_error = "La mise à jour du "+user.getFirstname()+" "+user.getLastname()+" a échoué.";
	}		
	
	logger.debug("Rédirige vers les détails du user créé...");
	return "redirect:/CONTACTMANAGER/user/" + user.getId();
	}

	/**
	 * Supprimer le user par son identifiant.
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping("user/delete/{id}")
	public String delete(@PathVariable Long id, Model model) {
	User deleteUser =	userService.deleteUser(id);
	
	if (deleteUser != null) 
		msg_success = "Le user "+deleteUser.getFirstname()+" "+deleteUser.getLastname()+" a été supprimé avec succès.";
	else 
		msg_error = "La suppression du user avec l'ID "+id+" a échoué.";
		
		model.addAttribute("active_menu", "listuser");
		return "redirect:/CONTACTMANAGER/users";
	}

}
