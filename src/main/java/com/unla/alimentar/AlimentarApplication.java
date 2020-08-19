package com.unla.alimentar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class }, scanBasePackages = { "com.unla.alimentar" })
public class AlimentarApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(AlimentarApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	    return application.sources(AlimentarApplication.class);
	}

}
