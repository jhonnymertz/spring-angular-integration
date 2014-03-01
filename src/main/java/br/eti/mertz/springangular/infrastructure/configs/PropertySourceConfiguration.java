package br.eti.mertz.springangular.infrastructure.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource({"classpath:config.properties"})
public class PropertySourceConfiguration {

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyPlaceHolder() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}