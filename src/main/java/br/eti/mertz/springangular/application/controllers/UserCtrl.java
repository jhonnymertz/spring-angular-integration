package br.eti.mertz.springangular.application.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.eti.mertz.springangular.application.controllers.advice.ServerException;
import br.eti.mertz.springangular.application.domain.access.User;
import br.eti.mertz.springangular.application.repositories.jpa.UserRepository;
import br.eti.mertz.springangular.application.services.UserService;

@Controller
@Scope("request")
@RequestMapping("/services")
public class UserCtrl {
	
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	private UserRepository userRepository;

	private UserService userService;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	@Autowired
	public UserCtrl(UserRepository userRepository, UserService userService) {
		this.userRepository = userRepository;
		this.userService = userService;
	}

	// -------------------------------------------------------------------------
	// REST Services
	// -------------------------------------------------------------------------
	
	@ResponseBody
	@RequestMapping(value="/users", method=RequestMethod.GET)
	public List<User> get() {
		return userRepository.findAll();
	}
	
	@ResponseBody
	@RequestMapping(value="/users/{id}", method=RequestMethod.GET)
	public User getById(@PathVariable Long id) {
		return userRepository.findOne(id);
	}
	
	@ResponseBody
	@RequestMapping(value="/users/{id}", method=RequestMethod.DELETE)
	public User deleteUser(@PathVariable Long id) throws ServerException {
		userService.delete(id);
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value="users", method=RequestMethod.POST)
	public User doInsertUser(@RequestBody User user) throws ServerException {
		return this.userService.save(user);
	}
	
	@ResponseBody
	@RequestMapping(value="profile", method=RequestMethod.GET)
	public User doGetProfile() {
		return this.userService.getCurrentUser();
	}
	
	@ResponseBody
	@RequestMapping(value="profile", method=RequestMethod.POST)
	public User doChangeProfile(@RequestBody User user) throws ServerException {
		return this.userService.save(user);
	}
}