package com.macrosoftas.contactmanager.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.macrosoftas.contactmanager.entities.Contact;
import com.macrosoftas.contactmanager.exception.ContactNotFound;
import com.macrosoftas.contactmanager.repositories.ContactRepository;

/**
 * Contact service implement.
 */
@Service
public class ContactServiceImpl implements ContactService {

	private ContactRepository contactRepository;

	private static List<Contact> contacts;
	
	@Autowired
	public void setContactRepository(ContactRepository contactRepository) {
		this.contactRepository = contactRepository;
	}

	@Override
	@Transactional
	public Iterable<Contact> listAllContacts() {
		return contactRepository.findAll();
	}

	@Override
	@Transactional
	public Contact getContactById(Integer id) {
		return contactRepository.getOne(id);
	}

	@Override
	@Transactional(rollbackFor= ContactNotFound.class)
	public Contact saveContact(Contact contact) {
		return contactRepository.save(contact);
	}

	@Override
	@Transactional(rollbackFor= ContactNotFound.class)
	public Contact deleteContact(Integer id) {
		
		Contact deletedContact = contactRepository.getOne(id);
				
		contactRepository.delete(deletedContact);
		return deletedContact;
		
	}
	
	@Override
	@Transactional(rollbackFor= ContactNotFound.class)
	public boolean isExist(Contact contact) {
		return findByFullName(contact.getNom(), contact.getPrenom())!=null;
	}
		
	public Contact findByFullName(String nom, String prenom) {
		for(Contact contact : contacts){
			if(contact.getNom().equalsIgnoreCase(nom) && contact.getPrenom().equalsIgnoreCase(prenom)){
				return contact;
			}
		}
		return null;
	}

}
