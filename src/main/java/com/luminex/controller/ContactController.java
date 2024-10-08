package com.luminex.controller;

import java.util.UUID;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.luminex.entities.Contact;
import com.luminex.entities.User;
import com.luminex.forms.ContactForm;
import com.luminex.forms.ContactSearchForm;
import com.luminex.helpers.AppConstants;
import com.luminex.helpers.Helper;
import com.luminex.helpers.Message;
import com.luminex.helpers.MessageType;
import com.luminex.services.ContactService;
import com.luminex.services.UserService;
import com.luminex.services.imageService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {

	private Logger logger = org.slf4j.LoggerFactory.getLogger(ContactController.class);

	@Autowired
	private ContactService contactService;

	@Autowired
	private imageService imageservice;

	@Autowired
	private UserService userService;

	@RequestMapping("/add")
	public String addContactView(Model model) {
		ContactForm contactForm = new ContactForm();
		model.addAttribute("ContactForm", contactForm);
		return "user/add_contact";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String saveContact(@ModelAttribute ContactForm contactForm, BindingResult result,
			Authentication authentication, HttpSession session) {

		if (result.hasErrors()) {
			session.setAttribute("message",
					Message.builder().content("Unable to Add the contact.").type(MessageType.red).build());
			return "user/add_contact";
		}

		String username = Helper.getEmailOfLoggedinUser(authentication);

		User user = userService.getUserByEmail(username);
		// System.out.println(contactForm);

		// logger.info("file information
		// :{}",contactForm.getProfileimage().getOriginalFilename());

		String filename = UUID.randomUUID().toString();
		String fileURL = imageservice.uploadImage(contactForm.getProfileimage(), filename);

		Contact contact = new Contact();
		contact.setName(contactForm.getName());
		contact.setFavourite(contactForm.isFavorite());
		contact.setEmail(contactForm.getEmail());
		contact.setPhoneNumber(contactForm.getPhoneNumber());
		contact.setAddress(contactForm.getAddress());
		contact.setDescription(contactForm.getDescription());
		contact.setUser(user);
		contact.setFacebookLink(contactForm.getFacebookLink());
		contact.setInstaLink(contactForm.getInstaLink());
		contact.setPicture(fileURL);
		contact.setCloudnaryImagePublicId(fileURL);

		contactService.save(contact);

		session.setAttribute("message",
				Message.builder().content("Contact Added Succesfully.").type(MessageType.green).build());

		return "redirect:/user/contacts/add";
	}

	@RequestMapping
	public String viewContacts(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE + "") int size,
			@RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
			@RequestParam(value = "direction", defaultValue = "asc") String direction, Model model,
			Authentication authentication) {

		String usernmae = Helper.getEmailOfLoggedinUser(authentication);
		User user = userService.getUserByEmail(usernmae);
		Page<Contact> pageContact = contactService.getbyUser(user, page, size, sortBy, direction);
		model.addAttribute("pageContact", pageContact);
		model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
		
		model.addAttribute("ContactSearchForm",new ContactSearchForm());
		return "user/contacts";
	}

	@GetMapping("/search")
	public String searchHandeler(@ModelAttribute ContactSearchForm contactSeacrchForm,
			@RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE + "") int size,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
			@RequestParam(value = "direction", defaultValue = "asc") String direction, Model model,Authentication authentication) {
		
		logger.info("field {} keyword {}", contactSeacrchForm.getField(), contactSeacrchForm.getValue());
		var user=userService.getUserByEmail(Helper.getEmailOfLoggedinUser(authentication));

		Page<Contact> pageContact = null;
		
		if (contactSeacrchForm.getField().equalsIgnoreCase("name")) {
			pageContact = contactService.searchByName( contactSeacrchForm.getValue(), size, page, sortBy, direction,user);
		} 
		else if (contactSeacrchForm.getField().equalsIgnoreCase("email")) {
			pageContact = contactService.searchByEmail( contactSeacrchForm.getValue(), size, page, sortBy, direction,user);

		} 
		else if (contactSeacrchForm.getField().equalsIgnoreCase("phone")) {
			pageContact = contactService.searchByPhoneNumber( contactSeacrchForm.getValue(), size, page, sortBy, direction,user);

		}
		logger.info("pageContact {}",pageContact);
		model.addAttribute("pageContact",pageContact);
		model.addAttribute("ContactSearchForm",contactSeacrchForm);
		model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
		
		return "user/search";
	}

	
	@RequestMapping("/delete/{contactid}")
	public String deleteContact(@PathVariable("contactid") String contactid) {
		
		contactService.delete(contactid);
		logger.info("contactid{} deleted",contactid);
		
		return "redirect:/user/contacts";
		
	}
	
	@GetMapping("/view/{contactid}")
public String updateContactFormView(@PathVariable("contactid") String contactid, Model model) {
		
		var contact=contactService.getByID(contactid);
		
		ContactForm contactForm=new ContactForm();
		contactForm.setName(contact.getName());
		contactForm.setEmail(contact.getEmail());
		contactForm.setPhoneNumber(contact.getPhoneNumber());
		contactForm.setAddress(contact.getAddress());
		contactForm.setDescription(contact.getDescription());
		contactForm.setFavorite(contact.isFavourite());
		contactForm.setFacebookLink(contact.getFacebookLink());
		contactForm.setInstaLink(contact.getInstaLink());
		contactForm.setPicture(contact.getPicture());
		
		model.addAttribute("ContactForm", contactForm);
		model.addAttribute("contactId", contactid);
		
		return "user/update_contact_view";
		
	}
	@RequestMapping(value="/update/{contactId}", method=RequestMethod.POST)
	public String updateContact(@PathVariable("contactId") String contactid,@ModelAttribute ContactForm contactForm,Model model) {
		
		var con=contactService.getByID(contactid);
		con.setId(contactid);
		con.setName(contactForm.getName());
		con.setEmail(contactForm.getEmail());
		con.setPhoneNumber(contactForm.getPhoneNumber());
		con.setAddress(contactForm.getAddress());
		con.setDescription(contactForm.getDescription());
		con.setFavourite(contactForm.isFavorite());
		con.setFacebookLink(contactForm.getFacebookLink());
		con.setInstaLink(contactForm.getInstaLink());
		
		if(contactForm.getProfileimage() != null && !contactForm.getProfileimage().isEmpty()) {
			String fileName=UUID.randomUUID().toString();
			String imageUrl=imageservice.uploadImage(contactForm.getProfileimage(), fileName);
			con.setCloudnaryImagePublicId(imageUrl);
			contactForm.setPicture(imageUrl);		
			}
		
		var updateCon=contactService.update(con);
		logger.info("upload contact{}",updateCon);
		model.addAttribute("message", Message.builder().content("Contact Updated Succesfully.").type(MessageType.green));		
		return "redirect:/user/contacts/view/"+contactid;
	}
	
}
