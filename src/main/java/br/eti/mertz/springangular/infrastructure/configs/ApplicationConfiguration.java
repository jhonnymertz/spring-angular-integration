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
		log.debug("Verifying if there are users...");
		
		if (userService.count() == 0) {
			createUser("Administrator", "admin", "admin", Profile.ADMINISTRATOR);
			createUser("Operator", "oper", "oper", Profile.OPERATOR);
		}
		
		log.debug("Users OK.");
	}

	private void createUser(String name, String username, String password, Profile profile) {
		log.debug("Creating user: {}", name);
		User user = new User();
		user.setProfile(profile);
		user.setPassword(password);
		user.setUsername(username);
		user.setEnabled(true);
		user.setName(name);
		userService.persist(user);
	}
	
}
