package com.luminex.services.impl;

import com.luminex.helpers.AppConstants;
import com.luminex.helpers.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.luminex.entities.User;
import com.luminex.repositories.UserRepo;
import com.luminex.services.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepo userRepo;

	private Logger logger=LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public User saveUser(User user) {
		String userId=UUID.randomUUID().toString();
		user.setUserId(userId);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		user.setRoleList(List.of(AppConstants.ROLE_USER));
		logger.info(user.getProvider().toString());
		return userRepo.save(user);
	}

	@Override
	public Optional<User> getUserById(String id) {
		// TODO Auto-generated method stub
		return userRepo.findById(id);
	}

	@Override
	public Optional<User> updateUser(User user) {
		// TODO Auto-generated method stub
		User user2=userRepo.findById(user.getUserId()).orElseThrow(()->new ResourceNotFoundException("User Not Found"));
		user2.setName(user.getName());
		user2.setEmail(user.getEmail());
		user2.setPassword(user.getPassword());
		user2.setAbout(user.getAbout());
		user2.setPhoneNo(user.getPhoneNo());
		user2.setProfilePic(user.getProfilePic());
		user2.setEnebled(user.isEnabled());
		user2.setEmailVerfied(user.isEmailVerfied());
		user2.setPhoneVerified(user.isPhoneVerified());
		user2.setProvider(user.getProvider());
		user2.setProviderUserId(user.getProviderUserId());
		
		User save=userRepo.save(user2);
		
		return Optional.ofNullable(save);
		
	}

	@Override
	public void deleteUser(String id) {
		User user2=userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("User Not Found"));
		userRepo.delete(user2);
	}

	@Override
	public boolean isUserExist(String userId) {
		User user2=userRepo.findById(userId).orElse(null);
		return user2!=null ? true:false;
	}

	@Override
	public boolean isUserExistByEmail(String email) {
		User user=userRepo.findByEmail(email).orElse(null);
		return user!=null ? true:false;
	}

	@Override
	public List<User> getAllUsers() {
		return userRepo.findAll();
	}

	@Override
	public User getUserByEmail(String email) {
		// TODO Auto-generated method stub
		return userRepo.findByEmail(email).orElse(null);
	}

}
