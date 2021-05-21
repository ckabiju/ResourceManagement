package com.ntt.resourcemanagement.Authentication_authorization.security;

import java.security.SecureRandom;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.ntt.resourcemanagement.Authentication_authorization.util.JwtTokenUtil;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Resource
	private DatabaseUserDetailsService databaseUserDetailsService;

	@Resource
	private JwtTokenUtil tokenUtil;

	private static final String[] AUTH_WHITELIST = { "/v2/api-docs", "/swagger-resources", "/swagger-resources/**",
			"/configuration/ui", "/configuration/security", "/swagger-ui.html", "/webjars/**" };

	/*
	 * In method configure we have restricted most APIs, only allowing Swagger URLs
	 * and signup URL. We also add filters to HttpSecurity. We will add our own
	 * UserDetailsServiceImpl class to validate user credentials.
	 */
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		// This code creates rules that requires authentication for all endpoints except
		// /registration and enables HTTP basic authentication.
		httpSecurity.cors().and().csrf().disable().authorizeRequests().antMatchers(AUTH_WHITELIST).permitAll()
				.antMatchers(HttpMethod.POST, "/v1/auth/signup").permitAll().anyRequest().authenticated().and()
				.addFilter(new AuthenticationFilter(authenticationManager(), tokenUtil))
				.addFilter(new AuthorizationFilter(authenticationManager(), tokenUtil)).sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(databaseUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12, new SecureRandom());
	}

}
