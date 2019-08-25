package com.macrosoftas.contactmanager.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.macrosoftas.contactmanager.entities.User;
import com.macrosoftas.contactmanager.exception.UserNotFound;
import com.macrosoftas.contactmanager.repositories.UserRepository;

/**
 * User service implement.
 */
@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;

	private static List<User> users;
	
	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	@Transactional
	public List<User> listAllUsers() {
		return userRepository.findAll();
	}

	@Override
	@Transactional
	public User getUserById(Long id) {
		return userRepository.getOne(id);
	}

	@Override
	@Transactional(rollbackFor= UserNotFound.class)
	public User saveUser(User user) {
		return userRepository.save(user);
	}

	@Override
	@Transactional(rollbackFor= UserNotFound.class)
	public User deleteUser(Long id) {
		
		User deletedUser = userRepository.getOne(id);
				
		userRepository.delete(deletedUser);
		return deletedUser;
		
	}
	
	@Override
	@Transactional(rollbackFor= UserNotFound.class)
	public boolean isExist(User user) {
		return findByFullName(user.getFirstname(), user.getLastname())!=null;
	}
		
	public User findByFullName(String nom, String prenom) {
		for(User user : users){
			if(user.getFirstname().equalsIgnoreCase(nom) && user.getLastname().equalsIgnoreCase(prenom)){
				return user;
			}
		}
		return null;
	}
	

	@Override
	public User findAllByUsername(String username) {
		// TODO Auto-generated method stub
		for(User user : users){
			if(user.getFirstname().equalsIgnoreCase(username) ){
				return user;
			}
		}
		return null;
	}

}
