package com.luminex.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.luminex.entities.User;
import com.luminex.helpers.Helper;
import com.luminex.services.UserService;


@Controller
@RequestMapping("/user")
public class UserController {
	
	private Logger logger=LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	

	
	@RequestMapping(value = "/dashboard")
	public String userDashboard() {
		return "user/dashboard";
	}
	@RequestMapping(value = "/profile")
	public String userProfile(Model model,Authentication authentication) {
		return "user/profile";
	}
	
}
