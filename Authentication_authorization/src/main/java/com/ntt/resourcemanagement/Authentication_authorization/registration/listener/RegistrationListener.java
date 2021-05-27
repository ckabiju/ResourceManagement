package com.ntt.resourcemanagement.Authentication_authorization.registration.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.ntt.resourcemanagement.Authentication_authorization.model.User;
import com.ntt.resourcemanagement.Authentication_authorization.registration.OnRegistrationCompleteEvent;
import com.ntt.resourcemanagement.Authentication_authorization.util.JwtTokenUtil;

@Component
public class RegistrationListener {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private MessageSource messages;

	@Autowired
	private JavaMailSender mailSender;

	@EventListener
	public void printEventText(OnRegistrationCompleteEvent event) {
		User user = (User) event.getSource();
		System.out.println("Print User name: " + user.getUserName());
		confirmRegistration(event);
	}

	private void confirmRegistration(final OnRegistrationCompleteEvent event) {
		final User user = (User) event.getSource();
		final String token = jwtTokenUtil.generateToken(user.getUserName(), 24 * 60 * 60);

		final SimpleMailMessage email = constructEmailMessage(event, user, token);
		mailSender.send(email);
	}

	//

	private SimpleMailMessage constructEmailMessage(final OnRegistrationCompleteEvent event, final User user,
			final String token) {
		final String recipientAddress = user.getEmail();
		final String subject = "Registration Confirmation";
		final String confirmationUrl = event.getAppUrl() + "/registrationConfirm.html?token=" + token;
		final String message = messages.getMessage("message.regSuccLink", null,
				"You registered successfully. To confirm your registration, please click on the below link.",
				event.getLocale());
		final SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(recipientAddress);
		email.setSubject(subject);
		email.setText(message + " \r\n" + confirmationUrl);
		// email.setFrom(env.getProperty("support.email"));
		return email;
	}
}
