package com.luminex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.luminex.entities.User;
import com.luminex.forms.UserForm;
import com.luminex.helpers.Message;
import com.luminex.helpers.MessageType;
import com.luminex.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class PageController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/")
	public String index() {
		return "redirect:/home";
	}

	@RequestMapping("/home")
	public String home() {
		System.out.println("Home page");
		return "home";
	}

	@RequestMapping("/about")
	public String about() {
		System.out.println("about page");
		return "about";
	}

	@RequestMapping("/service")
	public String service() {
		System.out.println("service page");
		return "service";
	}

	@RequestMapping("/contact")
	public String contact() {
		System.out.println("contact page");
		return "contact";
	}

	@RequestMapping("/login")
	public String login() {
		System.out.println("login page");
		return "login";
	}

	@RequestMapping("/signup")
	public String signup(Model model) {
		UserForm userform = new UserForm();
		model.addAttribute("userform", userform);
		return "signup";
	}

	@RequestMapping(value = "/doregister", method = RequestMethod.POST)
	public String doregister( @ModelAttribute UserForm userform,HttpSession session) {
		
		User user=new User();
		user.setName(userform.getName());
		user.setEmail(userform.getEmail());
		user.setPassword(userform.getPassword());
		user.setAbout(userform.getAbout());
		user.setPhoneNo(userform.getPhoneNumber());
		user.setProfilePic("https://www.google.com/url?sa=i&url=https%3A");
		
		User saveuser=userService.saveUser(user);
		System.out.println("User Saved");
		
		Message message=Message.builder().content("User Registered Successfully.").type(MessageType.green).build();
		session.setAttribute("message", message);
		
		return "redirect:/signup";
	}
}
