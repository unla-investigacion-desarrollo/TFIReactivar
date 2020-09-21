package com.unla.reactivar;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.unla.reactivar.security.JWTAuthorizationFilter;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class }, scanBasePackages = { "com.unla.reactivar" })
@EnableScheduling
public class ReactivarApplication extends SpringBootServletInitializer {

	@Value("${token_auth.validation.active:true}")
	private boolean isTokenValidationActive;

	private static final String[] AUTH_WHITELIST = { "/swagger-resources/**", "/swagger-ui.html", "/v2/api-docs",
			"/webjars/**" };

	public static void main(String[] args) {
		SpringApplication.run(ReactivarApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ReactivarApplication.class);
	}

	@EnableWebSecurity
	@Configuration
	class WebSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable();
			if (isTokenValidationActive) {
				http.addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
						.authorizeRequests().antMatchers(HttpMethod.POST, "/api/login").permitAll()
						.antMatchers(HttpMethod.POST, "/api/fisica").permitAll()
						.antMatchers(HttpMethod.POST, "/api/juridica").permitAll().antMatchers(AUTH_WHITELIST)
						.permitAll().anyRequest().authenticated();
			} else {
				http.authorizeRequests().anyRequest().permitAll();
			}
		}

	}
}
