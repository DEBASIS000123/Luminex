package com.luminex.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.luminex.entities.Contact;
import com.luminex.entities.User;

@Repository
public interface ContactRepo extends JpaRepository<Contact, String>{
	
	
	Page<Contact> findByUser(User user,Pageable pageable);
	
	 @Query("SELECT c FROM Contact c WHERE c.user.id = :userId")
	   List<Contact> findByUserId(@Param("userId") String userId);

	 
	 Page<Contact> findByUserAndNameContaining(User user,String nameKeyword,Pageable pageable);
	 Page<Contact> findByUserAndEmailContaining(User user,String emailKeyword,Pageable pageable);
	 Page<Contact> findByUserAndPhoneNumberContaining(User user,String phoneKeyword,Pageable pageable);
}
