package com.macrosoftas.contactmanager.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.macrosoftas.contactmanager.entities.Contact;
import com.macrosoftas.contactmanager.services.ContactService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.validation.Valid;

/**
 * Contact controller.
 */
@Controller
@RequestMapping(value = "/CONTACTMANAGER")
public class ContactController {

	private ContactService contactService;
	private String cmd="";
	private String msg_error;
	private String msg_success;
	
	private static final Logger logger = LoggerFactory.getLogger(ContactController.class);
	
	@Autowired
	public void setContactService(ContactService contactService) {
		this.contactService = contactService;
	}

	/**
	 * Liste tous les contacts.
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/contacts", method = RequestMethod.GET)
	public String list(Model model) {
		model.addAttribute("contacts", contactService.listAllContacts());
		model.addAttribute("active_menu", "listcontact");	
		model.addAttribute("error", msg_error);
		model.addAttribute("success", msg_success);
		return "contact/list";
	}

	/**
	 * Afficher un contact spécifique par son identifiant.
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("contact/{id}")
	public String showContact(@PathVariable Integer id, Model model) {
		model.addAttribute("contact", contactService.getContactById(id));
		model.addAttribute("active_menu", "listcontact");
		model.addAttribute("error", msg_error);
		model.addAttribute("success", msg_success);
		return "contact/show";
	}

	// Afficher le formulaire de modification du Contact
	@RequestMapping("contact/edit/{id}")
	public String edit(@PathVariable Integer id, Model model) {
		model.addAttribute("contact", contactService.getContactById(id));
		model.addAttribute("active_menu", "listcontact");
		model.addAttribute("cmd", "edit");
		cmd = "edit";
		return "contact/form";
	}

	/**
	 * Nouveau contact.
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("contact/new")
	public String newContact(Model model) {
		model.addAttribute("contact", new Contact());
		model.addAttribute("active_menu", "newcontact");
		model.addAttribute("cmd", "new");
		cmd = "new";
		return "contact/form";
	}

	/**
	 * Enregistrer le contact dans la base de données.
	 *
	 * @param contact
	 * @return
	 */
	@RequestMapping(value = "contact", method = RequestMethod.POST)
	public String saveContact(@Valid Contact contact, BindingResult bindingResult, Model model) {
		
		 logger.debug("dans contact => " + contact);
		 
		 //si erreur on fais passer ces erreurs à la vue
	        if (bindingResult.hasErrors()) {
	            logger.warn("Il y a des erreurs de validation");
	            List<ObjectError> allErrors = bindingResult.getAllErrors();
	            for (ObjectError oe : allErrors) {
	                logger.debug("Error => " + oe.getDefaultMessage());
	            }
	            
	            model.addAttribute("cmd", cmd);
	            return "contact/form";
	        }

	Contact currentContact	= contactService.saveContact(contact);
		
	if (currentContact != null) {
		if(cmd == "new")
			msg_success = "Un nouveau contact "+contact.getNom()+" "+contact.getPrenom()+" a été créé avec succès.";
		else
			msg_success = "Le contact a été mis à jour avec succès.";		
	}
	else {
		if(cmd == "new")
			msg_error = "La création du nouveau contact "+contact.getNom()+" "+contact.getPrenom()+" a échoué.";
		else
			msg_error = "La mise à jour du "+contact.getNom()+" "+contact.getPrenom()+" a échoué.";
	}		
	
	logger.debug("Rédirige vers les détails du contact créé...");
	return "redirect:/CONTACTMANAGER/contact/" + contact.getId();
	}

	/**
	 * Supprimer le contact par son identifiant.
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping("contact/delete/{id}")
	public String delete(@PathVariable Integer id, Model model) {
	Contact deleteContact =	contactService.deleteContact(id);
	
	if (deleteContact != null) 
		msg_success = "Le contact "+deleteContact.getNom()+" "+deleteContact.getPrenom()+" a été supprimé avec succès.";
	else 
		msg_error = "La suppression du contact avec l'ID "+id+" a échoué.";
		
		model.addAttribute("active_menu", "listcontact");
		return "redirect:/CONTACTMANAGER/contacts";
	}

}
