package br.eti.mertz.springangular.infrastructure.configs;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

@Configuration
@ImportResource({"classpath:/spring-security.xml"})
public class SecurityConfiguration extends AbstractSecurityWebApplicationInitializer{
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationEntryPoint servicesForbiddenEntryPoint() {
		return new AuthenticationEntryPoint() {

			@Override
			public void commence(HttpServletRequest request,
					HttpServletResponse response,
					AuthenticationException authException) throws IOException,
					ServletException {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

			}
		};
	}
}