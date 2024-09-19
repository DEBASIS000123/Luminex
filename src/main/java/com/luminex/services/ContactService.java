package com.luminex.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.luminex.entities.Contact;
import com.luminex.entities.User;

public interface ContactService {

	Contact save(Contact contact);
	
	Contact update(Contact contact);
	
	List<Contact> getAll();
	
	Contact getByID(String id);
	
	void delete(String id);
	
	List<Contact> search(String name,String email,String phoneNumber);
	
	List<Contact> getByUserId(String userID);
	
	Page<Contact> getbyUser(User user,int page,int size,String sortField,String sortDirection);
}
