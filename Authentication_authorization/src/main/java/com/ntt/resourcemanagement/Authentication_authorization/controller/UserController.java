package com.ntt.resourcemanagement.Authentication_authorization.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ntt.resourcemanagement.Authentication_authorization.model.User;
import com.ntt.resourcemanagement.Authentication_authorization.service.RegistrationService;

@CrossOrigin("http://localhost:8080")
@RestController
@RequestMapping("/v1/auth")
public class UserController {

	@Autowired
	RegistrationService registrationService;

	@PostMapping("/signup")
	public void signUp(@RequestBody User user) {
		registrationService.signUp(user);
	}
	
	@PostMapping("/signup")
	public void checkUserNameAvailability(@RequestParam String userName) {
		registrationService.checkUserNameAvailability(userName);
	}

	@PostMapping("/confirmEmail")
	public void confirmEmail(@RequestBody User user) {
		registrationService.signUp(user);
	}

}
