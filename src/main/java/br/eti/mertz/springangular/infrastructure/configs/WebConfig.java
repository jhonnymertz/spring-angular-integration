package br.eti.mertz.springangular.infrastructure.configs;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages="br.eti.mertz.springangular.application")
public class WebConfig extends WebMvcConfigurerAdapter {
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String[] resources = {"**/*.js", "**/*.css", "**/*.html", "**/*.png", "**/*.ico", "**/*.woff", "**/*.ttf", "**/*.svg", "**/*.eot"};
		registry.addResourceHandler(resources).addResourceLocations("/");
	}
}
