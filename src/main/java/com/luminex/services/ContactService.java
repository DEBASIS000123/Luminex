package com.luminex.services;

import java.util.List;

import com.luminex.entities.Contact;

public interface ContactService {

	Contact save(Contact contact);
	
	Contact update(Contact contact);
	
	List<Contact> getAll();
	
	Contact getByID(String id);
	
	void delete(String id);
	
	List<Contact> search(String name,String email,String phoneNumber);
	
	List<Contact> getByUserId(String userID);
}