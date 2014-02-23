package br.eti.mertz.springangular.infrastructure.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Classe para configuração dos parâmetros de configuração da aplicação. 
 * Obtém os parâmetros de acordo com o perfil ativo do Spring
 */
@Configuration
@PropertySource({"classpath:config.properties"})
public class PropertySourceConfiguration {
	/**
	 * Fornece o bean para resolver os parâmetros presentes nos properties. 
	 * Habilita anotações como @Value
	 * 
	 * @see PropertySourcesPlaceholderConfigurer
	 * @return Bean para manipulação dos properties
	 */
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyPlaceHolder() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}