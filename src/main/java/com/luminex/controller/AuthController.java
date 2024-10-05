package com.luminex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.luminex.entities.User;
import com.luminex.repositories.UserRepo;


@Controller
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private UserRepo userRepo;

	@GetMapping("/verify-email")
	public String verifyEmail(@RequestParam("token") String token) {
		
		System.out.println("Verify Email");
		User user=userRepo.findByEmailToken(token).orElse(null);
		
		if(user!=null){
			if(user.getEmailToken().equals(token)) {
				user.setEmailVerfied(true);
				user.setEnebled(true);
				userRepo.save(user);
				
				return "success_page";
			}
		}
		else {
			return "error_page";
		}
		
		return new String();
	}
	
}
