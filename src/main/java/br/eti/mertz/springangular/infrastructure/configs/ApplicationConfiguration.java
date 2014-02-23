package br.eti.mertz.springangular.infrastructure.configs;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import br.eti.mertz.springangular.application.domain.access.Profile;
import br.eti.mertz.springangular.application.domain.access.User;
import br.eti.mertz.springangular.application.services.UserService;

@Configuration
public class ApplicationConfiguration {
	
	private Logger log = LoggerFactory.getLogger(ApplicationConfiguration.class);
	
	@Autowired
	private UserService userService;
	
	@PostConstruct
	public void init() {
		createUsers();
	}
	
	private void createUsers() {
		log.debug("Verificando se já existem usuários...");
		
		if (userService.count() == 0) {
			createUser("Administrador", "admin", "admin", Profile.ADMINISTRATOR);
			createUser("Administrador", "oper", "oper", Profile.OPERATOR);
		}
		
		log.debug("Usuários OK.");
	}

	private void createUser(String name, String username, String password, Profile profile) {
		log.debug("Criando Usuário: {}", name);
		User user = new User();
		user.setProfile(profile);
		user.setPassword(password);
		user.setUsername(username);
		user.setEnabled(true);
		user.setName(name);
		userService.persist(user);
	}
	
}
