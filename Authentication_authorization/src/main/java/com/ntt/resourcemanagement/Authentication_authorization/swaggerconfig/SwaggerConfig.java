package com.ntt.resourcemanagement.Authentication_authorization.swaggerconfig;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

	@Bean
    public Docket resourceManagementAuthApi() {
		// Docket decides what needs to be documented
        return new Docket(DocumentationType.SWAGGER_2)
        		.apiInfo(apiInfo())
        		.securitySchemes(Collections.singletonList(apiKey()))
        		.securityContexts(Collections.singletonList(securityContext()))
                .select()
                .paths(regex("/auth.*"))
                .apis(RequestHandlerSelectors.basePackage("com.ntt"))
                .build()
                ;
    }
 
	private ApiKey apiKey() {
        return new ApiKey("Authorization", "Authorization", "headers");
    } 
	
	
	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(defaultAuth()).build();
	}

	private List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Collections.singletonList(new SecurityReference("Authorization", authorizationScopes));
	}

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("ResourceManagement Authentication API")
                .description("Provides API's to handle authentication and Authorization request")
                .license("API License")
                //.licenseUrl("https://opensource.org/licenses/MIT")
                .build();
    }
   
}
