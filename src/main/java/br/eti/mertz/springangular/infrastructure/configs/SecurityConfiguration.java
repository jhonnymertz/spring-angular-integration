package br.eti.mertz.springangular.infrastructure.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

@Configuration
@ImportResource({"classpath:/spring-security.xml"})
public class SecurityConfiguration extends AbstractSecurityWebApplicationInitializer{
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationSuccessHandler authenticationSuccessHandler() {
		SimpleUrlAuthenticationSuccessHandler handler;
		handler = new SimpleUrlAuthenticationSuccessHandler();
		handler.setTargetUrlParameter("url");
		return handler;
	}
	
	@Bean
	public AuthenticationEntryPoint servicesForbiddenEntryPoint() {
		return new Http403ForbiddenEntryPoint();
	}
}