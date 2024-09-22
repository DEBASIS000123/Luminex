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
	
	Page<Contact> searchByName(String nameKeyword,int size,int page, String sortBy,String order,User user);
	Page<Contact> searchByEmail(String emailKeyword,int size,int page, String sortBy,String order,User user);
	Page<Contact> searchByPhoneNumber(String phoneNumberKeyword,int size,int page, String sortBy,String order,User user);
	
	List<Contact> getByUserId(String userID);
	
	Page<Contact> getbyUser(User user,int page,int size,String sortField,String sortDirection);
	
	
}
