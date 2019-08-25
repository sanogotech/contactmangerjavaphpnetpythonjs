package com.macrosoftas.contactmanager.controllers;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.macrosoftas.contactmanager.entities.Contact;
import com.macrosoftas.contactmanager.exception.ContactNotFound;
import com.macrosoftas.contactmanager.services.ContactService;


@RestController
@RequestMapping(value="/contact")
public class ContactRestController {
	
	@Autowired
	/**
	 * Service qui effectuera tous les manipulations de la table.
	 * Method validation: ResquestBody
	 */
	
	private ContactService contactService; 
	
	private static final Logger logger = LoggerFactory.getLogger(ContactRestController.class);
	    
	/**
	 * Récupérer tous les contact.
	 * url : http://localhost:8080/contact/readall
	 *
	 * @param nothing
	 * @return
	 */
	
    @RequestMapping(value = "/readall", method = RequestMethod.GET)
    public ResponseEntity<List<Contact>> getAllContacts() {
    	 logger.info("Récupération des contacts");
        List<Contact> contacts = (List<Contact>) contactService.listAllContacts();
        if(contacts.isEmpty()){
        	 logger.error("Aucun enregistremet trouvé");
            return new ResponseEntity<List<Contact>>(HttpStatus.NO_CONTENT);//au cas où la liste est vide on retourne HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Contact>>(contacts, HttpStatus.OK);
    } 
      
    /**
	 * Récupérer un seul contact.
	 * url : http://localhost:8080/contact/read/id
	 * 
	 * @param id
	 * @return
	 */
    @RequestMapping(value = "/read/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Contact> getContact(@PathVariable("id") int id) {
       logger.info("Récupération du contact avec ID : " + id);
        
        Contact contact = contactService.getContactById(id);
        if (contact == null) {
            logger.error("Contact avec l'identifiant " + id + " pas trouvé");
            return new ResponseEntity<Contact>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Contact>(contact, HttpStatus.OK);
    }	
  
    /**
   	 * Créer un contact.
   	 * url : http://localhost:8080/contact/add
   	 * 
   	 * @return
   	 */       
       
    @RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity<Contact> addContact(@RequestBody Contact contact) {
    	contactService.saveContact(contact);
		logger.debug("Added:: " + contact);
		return new ResponseEntity<Contact>(contact, HttpStatus.CREATED);
	}
     
    /**
   	 * Mettre  à jour un contact .
   	 * url : http://localhost:8080/contact/update/id
   	 *
   	 * @param id
   	 * @return
   	 */
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Contact> updateContact(@PathVariable("id") int id, @Valid @RequestBody Contact contact) throws ContactNotFound {
    	  logger.info("Mise à jour du contact ID : " + id);
         
        Contact currentContact = contactService.getContactById(id);
        currentContact =   contactService.saveContact(contact);;
         
        if (currentContact==null) {
            logger.error("Contact avec l'ID " + id + " pas trouvé");
            return new ResponseEntity<Contact>(HttpStatus.NOT_FOUND);
        }                
       
        return new ResponseEntity<Contact>(currentContact, HttpStatus.OK);
    }
	
    /**
   	 * Supprimer un contact .
   	 * url : http://localhost:8080/contact/delete/id
   	 * @param id
   	 * @return
   	 */ 
    
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Contact> deleteContact(@PathVariable("id") int id) throws ContactNotFound {
        logger.info("\n" + "Récupération et suppression d'un contact avec 'ID : " + id);
 
        Contact contact = contactService.getContactById(id);
        if (contact == null) {
          logger.error("\n" + "Impossible de supprimer. Contact avec id " + id + " pas trouv�");
            return new ResponseEntity<Contact>(HttpStatus.NOT_FOUND);
        }
 
        contactService.deleteContact(id);
        return new ResponseEntity<Contact>(HttpStatus.NO_CONTENT);
    }
	
  
}
