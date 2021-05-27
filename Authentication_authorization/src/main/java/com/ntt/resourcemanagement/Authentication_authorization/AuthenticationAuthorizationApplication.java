package com.ntt.resourcemanagement.Authentication_authorization;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.context.request.RequestContextListener;

@SpringBootApplication
@EnableMongoRepositories(basePackages = { "com.ntt.resourcemanagement.Authentication_authorization" })
public class AuthenticationAuthorizationApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationAuthorizationApplication.class, args);
	}

	@Bean
	public RequestContextListener requestContextListener() {
		return new RequestContextListener();
	}
	
	@Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
	
}
