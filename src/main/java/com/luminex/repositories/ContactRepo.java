package com.luminex.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.luminex.entities.Contact;
import com.luminex.entities.User;

public interface ContactRepo extends JpaRepository<Contact, String>{
	
	
	List<Contact> findByUser(User user);
	
	 @Query("SELECT c FROM Contact c WHERE c.user.id = :userId")
	   List<Contact> findByUserId(@Param("userId") String userId);

	 
	
	
}
