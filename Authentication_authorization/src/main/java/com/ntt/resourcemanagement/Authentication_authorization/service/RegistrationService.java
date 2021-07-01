package com.ntt.resourcemanagement.Authentication_authorization.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mongodb.MongoWriteException;
import com.ntt.resourcemanagement.Authentication_authorization.model.User;
import com.ntt.resourcemanagement.Authentication_authorization.repositories.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
public class RegistrationService {

	@Value("${send.verification.email}")
	Boolean sendVerificationEmail;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	public User signUp(User user) {
		// Validate User details

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setConfirmed(!sendVerificationEmail);
		
		// create a verificationToken
		return userRepository.save(user);
	}
	
	public boolean checkUserNameAvailability(String userName) {
		return userRepository.findByUserName(userName) == null;
	}
}
