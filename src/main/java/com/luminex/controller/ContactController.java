package com.luminex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.luminex.entities.Contact;
import com.luminex.entities.User;
import com.luminex.forms.ContactForm;
import com.luminex.helpers.Helper;
import com.luminex.helpers.Message;
import com.luminex.helpers.MessageType;
import com.luminex.services.ContactService;
import com.luminex.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
@RequestMapping("/user/contacts")
public class ContactController {
	
	@Autowired
	private ContactService contactService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/add")
	public String addContactView(Model model) {
		ContactForm contactForm=new ContactForm();
		model.addAttribute("ContactForm",contactForm);
		return "user/add_contact";
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String saveContact( @ModelAttribute ContactForm contactForm,  BindingResult result,Authentication authentication,HttpSession session) {
		
		if(result.hasErrors()) {
			session.setAttribute("message", Message.builder().content("Unable to Add the contact.").type(MessageType.red).build());
			return "user/add_contact";
		}
		
		
		
		
		
		String username=Helper.getEmailOfLoggedinUser(authentication);
		
		User user= userService.getUserByEmail(username);
		//System.out.println(contactForm);
		
		Contact contact =new Contact();
		contact.setName(contactForm.getName());
        contact.setFavourite(contactForm.isFavorite());
        contact.setEmail(contactForm.getEmail());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setUser(user);
        contact.setFacebookLink(contactForm.getFacebookLink());
        contact.setInstaLink(contactForm.getInstaLink());
		
		contactService.save(contact);
		
		session.setAttribute("message", Message.builder().content("Contact Added Succesfully.").type(MessageType.green).build());
		return "redirect:/user/contacts/add";
	}
	
	
}
