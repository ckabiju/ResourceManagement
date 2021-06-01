package com.ntt.resourcemanagement.Authentication_authorization.controller;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ntt.resourcemanagement.Authentication_authorization.dto.UserDto;
import com.ntt.resourcemanagement.Authentication_authorization.model.User;
import com.ntt.resourcemanagement.Authentication_authorization.registration.OnRegistrationCompleteEvent;
import com.ntt.resourcemanagement.Authentication_authorization.service.RegistrationService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin("http://localhost:8080")
@RestController
@RequestMapping("/auth/v1")
public class UserController {

	@Autowired
	RegistrationService registrationService;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	ApplicationEventPublisher eventPublisher;
	
	@Value("${send.verification.email}")
	Boolean sendVerificationEmail;
	
	@PostMapping("/signup")
	@ApiOperation(value = "User Registration",
			notes = "Validates the user details and register the user. Also sends out confirmation email to the registered email id."
			//response =""
			)
	public void signUp(
			@RequestBody UserDto userDto, HttpServletRequest request) {
		User user = modelMapper.map(userDto, User.class);
		user = registrationService.signUp(user);
		
		// Sendout email for account confirmation 
		//-Which includes a confirmation link with the VerificationToken's value
		 String appUrl = request.getContextPath();
			if(sendVerificationEmail) {
			  eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user,
			  request.getLocale(), appUrl));
			}
	}
	
	@PostMapping("/checkUserNameAvailability")
	public Boolean checkUserNameAvailability(@RequestParam String userName) {
		return registrationService.checkUserNameAvailability(userName);
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
	@ApiOperation(value = "Check Authorization token is valid.",
	notes ="Returns a String. 'Authorization token is valid'",
	response = String.class
	)
	public String ping() {
		return "Authorization token is valid";
	}
	
	@GetMapping("/noAuthPing")
	@ApiOperation(value = "Check server is up and running.",
	notes ="Returns a String. 'Server is up and running'",
	response = String.class
	)
	public String noAuthPing() {
		return "Server is up and running";
	}
	

}
