package com.macrosoftas.contactmanager.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.macrosoftas.contactmanager.entities.Contact;
import com.macrosoftas.contactmanager.entities.User;
import com.macrosoftas.contactmanager.forms.LoginForm;
import com.macrosoftas.contactmanager.services.LoginService;
import com.macrosoftas.contactmanager.services.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.util.List;


@RestController
@RequestMapping(value = "/api")
public class AccountRestController {

	private static final Logger logger = LoggerFactory.getLogger(AccountRestController.class);

	@Autowired
	private LoginService loginService;
	private UserService userService;


	/**
	 * Connexion de l'utilisateur
	 * 	
	 * @param username
	 * @param password
	 * @param model
	 * @return
	 */       
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<LoginForm> Login( @Valid @RequestBody LoginForm loginForm) {

		logger.debug("Login:: " + loginForm);

		if (! loginService.authentice(loginForm.getUsername(),
				loginForm.getPassword())) {
			logger.error("Le nom d'utilisateur ou le mot de passe est incorrect. Veuillez réessayer!");
			return new ResponseEntity<LoginForm>(HttpStatus.NOT_FOUND);
		}                

		return new ResponseEntity<LoginForm>(loginForm, HttpStatus.OK);
	}


}
