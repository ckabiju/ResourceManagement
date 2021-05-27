package com.ntt.resourcemanagement.Authentication_authorization.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ntt.resourcemanagement.Authentication_authorization.model.User;
import com.ntt.resourcemanagement.Authentication_authorization.repositories.UserRepository;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUserName(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}else if (!user.isConfirmed()) {
			throw new UsernameNotFoundException(username+" please verify your account by clicking the link in verification email you received.");
		}
		

		UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(user.getUserName())
				.password(user.getPassword()).authorities("USER").build();
		return userDetails;
	}

}
