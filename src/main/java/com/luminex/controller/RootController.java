package com.luminex.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.luminex.entities.User;
import com.luminex.helpers.Helper;
import com.luminex.services.UserService;

@ControllerAdvice
public class RootController {
	private Logger logger=LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private  UserService userService;
	
	
	@ModelAttribute
	public void addLogedinUserInformation(Model model,Authentication authentication) {
		if(authentication==null) {
			return;
		}
		String username=Helper.getEmailOfLoggedinUser(authentication);
		logger.info("User logged in:{}",username);
		User user=userService.getUserByEmail(username);
		
		System.out.println(user.getName());
		System.out.println(user.getEmail());
		
		model.addAttribute("loggedinUser",user);

	}
	
	
}
