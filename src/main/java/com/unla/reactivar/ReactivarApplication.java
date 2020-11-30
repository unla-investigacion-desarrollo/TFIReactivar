package com.unla.reactivar;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.google.common.collect.ImmutableList;
import com.unla.reactivar.security.JWTAuthorizationFilter;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class }, scanBasePackages = { "com.unla.reactivar" })
@EnableScheduling
@CrossOrigin(origins = "*")
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
	
	@Bean
	public WebMvcConfigurer configure() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("*").allowCredentials(false).allowedHeaders("*").allowedMethods("*").exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials");
			}
		};
	}
	
	@EnableWebSecurity
	@Configuration
	class WebSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.cors().configurationSource(this.corsConfigurationSource()).and().csrf().disable().sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().headers().addHeaderWriter(
                    new StaticHeadersWriter("Access-Control-Allow-Origin", "*"));
			if (isTokenValidationActive) {
				http.addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
						.authorizeRequests().antMatchers(HttpMethod.POST, "/api/login").permitAll()
						.antMatchers(HttpMethod.POST, "/api/fisica").permitAll()
						.antMatchers(HttpMethod.GET, "/api/provincia").permitAll()
						.antMatchers(HttpMethod.GET, "/api/provincia/{idProvincia}/localidades").permitAll()
						.antMatchers(HttpMethod.POST, "/api/ocupacionLocal/{idEmprendimientoBase64}").permitAll()
						.antMatchers(HttpMethod.POST, "/api/juridica").permitAll()
						.antMatchers(HttpMethod.POST, "/api/persona/resetPassword").permitAll()
						.antMatchers(HttpMethod.GET, "/api/persona/changePassword").permitAll()
						.antMatchers(HttpMethod.POST, "/api/persona/savePassword").permitAll()
						.antMatchers(HttpMethod.GET, "/api/persona/validateEmail").permitAll()
						.antMatchers(HttpMethod.GET, "/api/persona/resendValidationEmail").permitAll()
						.antMatchers(AUTH_WHITELIST)
						.permitAll().anyRequest().authenticated();
			} else {
				http.authorizeRequests().anyRequest().permitAll();
			}
			
		}
		
		@Bean
	    public CorsConfigurationSource corsConfigurationSource() {
	        final CorsConfiguration configuration = new CorsConfiguration();
	        configuration.setAllowedOrigins(ImmutableList.of("*"));
	        configuration.setAllowedMethods(ImmutableList.of("*"));
	        // setAllowCredentials(true) is important, otherwise:
	        // The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
	        configuration.setAllowCredentials(true);
	        // setAllowedHeaders is important! Without it, OPTIONS preflight request
	        // will fail with 403 Invalid CORS request
	        configuration.setAllowedHeaders(ImmutableList.of("*"));
	        configuration.setExposedHeaders(ImmutableList.of("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
	      
	        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        source.registerCorsConfiguration("/**", configuration);
	        return source;
	    }

	}
	
	 
}
