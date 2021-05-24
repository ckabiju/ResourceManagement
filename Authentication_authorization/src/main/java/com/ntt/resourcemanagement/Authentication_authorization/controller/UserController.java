package com.ntt.resourcemanagement.Authentication_authorization.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ntt.resourcemanagement.Authentication_authorization.model.User;
import com.ntt.resourcemanagement.Authentication_authorization.service.RegistrationService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin("http://localhost:8080")
@RestController
@RequestMapping("/auth/v1")
public class UserController {

	@Autowired
	RegistrationService registrationService;

	
	@PostMapping("/signup")
	@ApiOperation(value = "User Registration",
			notes = "Validates the user details and register the user. Also sends out confirmation email to the registered email id."
			//response =""
			)
	public void signUp(
			@RequestBody User user) {
		registrationService.signUp(user);
	}
	
	@PostMapping("/checkUserNameAvailability")
	public void checkUserNameAvailability(@RequestParam String userName) {
		registrationService.checkUserNameAvailability(userName);
	}

	@PostMapping("/confirmEmail")
	public void confirmEmail(@RequestBody User user) {
		registrationService.signUp(user);
	}
	
	@GetMapping("/ping")
	/*This can be used to provide Authentication for single method in SwaggerUI.
	 * For this application we are enabling it at global level in SwagerConfig.java.
	 * 
	 * @ApiImplicitParams({
	 * 
	 * @ApiImplicitParam(name = "Authorization", value = "Authorization token",
	 * required = true, dataType = "string", paramType = "header") })
	 */
	public String ping() {
		return "Successful response from ping API";
	}
	
	@GetMapping("/noAuthPing")
	public String noAuthPing() {
		return "Successful response from noAuthPing API";
	}
	

}
