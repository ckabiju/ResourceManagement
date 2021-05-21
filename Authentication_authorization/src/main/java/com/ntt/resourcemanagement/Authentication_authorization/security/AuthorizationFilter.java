package com.ntt.resourcemanagement.Authentication_authorization.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.ntt.resourcemanagement.Authentication_authorization.util.JwtTokenUtil;

public class AuthorizationFilter extends BasicAuthenticationFilter {

	private final JwtTokenUtil jwtTokenUtil;

	public AuthorizationFilter(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil) {
		super(authenticationManager);
		this.jwtTokenUtil = jwtTokenUtil;
	}

	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		String header = request.getHeader("Authorization");
		if (header == null || !header.startsWith("Bearer")) {
			filterChain.doFilter(request, response);
			return;
		}

		UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(request);
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		filterChain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if (token != null) {
			String[] tokenParts = token.split(" ");
			String user = jwtTokenUtil.validateAndReturnUser(tokenParts[1]);
			if (user != null) {
				return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
			}
			return null;
		}
		return null;
	}
}
