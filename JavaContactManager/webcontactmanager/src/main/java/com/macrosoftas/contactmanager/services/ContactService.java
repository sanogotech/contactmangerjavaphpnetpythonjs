package com.macrosoftas.contactmanager.services;

import com.macrosoftas.contactmanager.entities.Contact;

public interface ContactService {

	Iterable<Contact> listAllContacts(); // fonction pour récupérer tous les contacts

	Contact getContactById(Integer id); // fonction pour récupérer un contact en fonction du ID

	Contact saveContact(Contact contact); // fonction pour créer et mettre à jour un contact

	Contact deleteContact(Integer id); // fonction pour supprimer un contact en fonction de son ID
		
	boolean isExist(Contact contact);//vérifier si le contact existe	
	
	Contact findByFullName(String firstname, String lastname);//récupérer un contact par le nom et prénom

}
