package com.macrosoftas.contactmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.macrosoftas.contactmanager.entities.Contact;

public interface ContactRepository extends JpaRepository<Contact, Integer> {

	
}
