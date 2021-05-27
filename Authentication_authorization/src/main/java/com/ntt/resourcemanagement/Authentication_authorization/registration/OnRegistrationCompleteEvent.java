package com.ntt.resourcemanagement.Authentication_authorization.registration;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import com.ntt.resourcemanagement.Authentication_authorization.model.User;

import lombok.Getter;

@SuppressWarnings("serial")
@Getter
public class OnRegistrationCompleteEvent extends ApplicationEvent {

	private String appUrl;
	private Locale locale;
	private User user;

	public OnRegistrationCompleteEvent(final User user, final Locale locale, final String appUrl) {
		super(user);
		this.user = user;
		this.locale = locale;
		this.appUrl = appUrl;
	}

}
