package com.luminex.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luminex.entities.Contact;
import com.luminex.repositories.ContactRepo;
import com.luminex.services.ContactService;

@Service
public class ContactServiceImpl implements ContactService{
	
	@Autowired 
	private ContactRepo contactRepo;

	@Override
	public Contact save(Contact contact) {
	String contactId=UUID.randomUUID().toString();
	contact.setId(contactId);
	return contactRepo.save(contact);
	}

	@Override
	public Contact update(Contact contact) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Contact> getAll() {
		// TODO Auto-generated method stub
		return contactRepo.findAll();
	}

	@Override
	public Contact getByID(String id) {
		// TODO Auto-generated method stub
		return contactRepo.findById(id).orElseThrow(()-> new RuntimeException("User Not Found"));
	}

	@Override
	public void delete(String id) {
		var contt=contactRepo.findById(id).orElseThrow(()-> new RuntimeException("User Not Found"));
		
		contactRepo.delete(contt);
	}

	@Override
	public List<Contact> search(String name, String email, String phoneNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Contact> getByUserId(String userID) {
		// TODO Auto-generated method stub
		return contactRepo.findByUserId(userID);
	}

}
