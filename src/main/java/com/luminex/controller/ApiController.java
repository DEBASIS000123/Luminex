package com.luminex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luminex.entities.Contact;
import com.luminex.services.ContactService;

@RestController
@RequestMapping("/api")
public class ApiController {

	@Autowired
	private ContactService contactService;
	
	@GetMapping("/contacts/{contactId}")
	public Contact getMethodName(@PathVariable String contactId) {
		return contactService.getByID(contactId);
	}
	
}
