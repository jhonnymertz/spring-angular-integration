package br.eti.mertz.springangular.application.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.eti.mertz.springangular.application.domain.access.Profile;
import br.eti.mertz.springangular.application.domain.access.User;
import br.eti.mertz.springangular.application.services.UserService;

@Controller
@RequestMapping("/services/authenticate")
public class AuthenticationCtrl {

	static final Logger logger = LoggerFactory
			.getLogger(AuthenticationCtrl.class);

	@Autowired
	private UserService userService;

	@ResponseBody
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public User doGetUser() {
		return this.userService.getCurrentUser();
	}

	@ResponseBody
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public Profile doGetProfile() {
		return this.userService.getCurrentUser().getProfile();
	}

}
