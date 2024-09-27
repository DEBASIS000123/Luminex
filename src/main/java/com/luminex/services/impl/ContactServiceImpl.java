package com.luminex.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.luminex.entities.Contact;
import com.luminex.entities.User;
import com.luminex.helpers.ResourceNotFoundException;
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
		var contactOld=	contactRepo.findById(contact.getId()).orElseThrow(()-> new ResourceNotFoundException("COntact Not Found"));
		
		contactOld.setName(contact.getName());
		contactOld.setEmail(contact.getEmail());
		contactOld.setPhoneNumber(contact.getPhoneNumber());
		contactOld.setAddress(contact.getAddress());
		contactOld.setDescription(contact.getDescription());
		contactOld.setPicture(contact.getPicture());
		contactOld.setFavourite(contact.isFavourite());
		contactOld.setFacebookLink(contact.getFacebookLink());
		contactOld.setInstaLink(contact.getInstaLink());
		contactOld.setCloudnaryImagePublicId(contact.getCloudnaryImagePublicId());
		
		
		
		return contactRepo.save(contactOld);
		
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
	public List<Contact> getByUserId(String userID) {
		// TODO Auto-generated method stub
		return contactRepo.findByUserId(userID);
	}

	@Override
	public Page<Contact> getbyUser(User user,int page ,int size,String sortBy,String direction) {
		
		Sort sort=direction.equals("desc")? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		
		var pageable=PageRequest.of(page, size,sort);
		
		
		return contactRepo.findByUser(user,pageable);
		 
	}

	@Override
	public Page<Contact> searchByName(String nameKeyword, int size, int page, String sortBy, String order,User user) {
		Sort sort=order.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
		var pageable=PageRequest.of(page, size,sort);
		return contactRepo.findByUserAndNameContaining(user,nameKeyword, pageable);
	}

	@Override
	public Page<Contact> searchByEmail(String emailKeyword, int size, int page, String sortBy, String order,User user) {
		Sort sort=order.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
		var pageable=PageRequest.of(page, size,sort);
		return contactRepo.findByUserAndEmailContaining(user,emailKeyword, pageable);
	}

	@Override
	public Page<Contact> searchByPhoneNumber(String phoneNumberKeyword, int size, int page, String sortBy,
			String order,User user) {
		Sort sort=order.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
		var pageable=PageRequest.of(page, size,sort);
		return contactRepo.findByUserAndPhoneNumberContaining(user,phoneNumberKeyword, pageable);
	}


}
