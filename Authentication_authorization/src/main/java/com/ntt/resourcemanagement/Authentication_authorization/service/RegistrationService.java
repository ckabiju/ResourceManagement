package com.ntt.resourcemanagement.Authentication_authorization.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ntt.resourcemanagement.Authentication_authorization.model.User;
import com.ntt.resourcemanagement.Authentication_authorization.repositories.UserRepository;

@Service
public class RegistrationService {

	@Autowired
	UserRepository userRepository;

	public void signUp(User user) {
		
		userRepository.save(user);
	}
	
	public boolean checkUserNameAvailability(String userName) {
		return userRepository.findByUserName(userName) == null;
	}
}
