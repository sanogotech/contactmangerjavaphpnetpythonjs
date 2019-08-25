package com.macrosoftas.contactmanager.controllers;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Homepage controller.
 */
@Controller
public class HomeController {

	@Autowired
	/*
	 * Déclaration variables locales
	 */

	HttpSession session;
	
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@RequestMapping(value = { "/", "/CONTACTMANAGER/home", "/CONTACTMANAGER" })
	String Home() {
		logger.info("Start  HomePage ContactactManager ");

		if (session.isNew()) {
			logger.debug("Session Http is New");
			return "redirect:/CONTACTMANAGER/";
		}else {
			//session.getAttribute("username");
			return "home/index";
		}
	}

}
