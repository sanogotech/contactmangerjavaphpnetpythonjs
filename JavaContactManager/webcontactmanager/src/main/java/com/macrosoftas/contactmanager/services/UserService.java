package com.macrosoftas.contactmanager.services;

import java.util.List;

import com.macrosoftas.contactmanager.entities.User;

public interface UserService {

	List<User> listAllUsers(); // fonction pour récupérer tous les users

	User getUserById(Long id); // fonction pour récupérer un user en fonction du ID

	User saveUser(User user); // fonction pour créer et mettre à jour un user

	User deleteUser(Long id); // fonction pour supprimer un user en fonction de son ID
		
	boolean isExist(User user);//vérifier si le user existe	
	
	User findByFullName(String firstname, String lastname);//récupérer un user par le nom et prénom
	
	User findAllByUsername(String username);//récupérer un user en fonction du nom d'utilisateur

}
