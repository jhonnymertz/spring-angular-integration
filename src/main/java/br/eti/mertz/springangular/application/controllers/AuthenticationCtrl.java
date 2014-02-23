package br.eti.mertz.springangular.application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.eti.mertz.springangular.application.domain.access.User;
import br.eti.mertz.springangular.application.services.TokenUtils;
import br.eti.mertz.springangular.application.services.UserService;

@Controller
@RequestMapping("/services")
public class AuthenticationCtrl {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TokenUtils tokenUtils; 
	
	@Autowired
	@Qualifier("authenticationManager")
	private AuthenticationManager authManager;
	
	@ResponseBody
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST, produces = "application/json")
	public User authenticate(@RequestParam("username") String username, @RequestParam("password") String password){
		UsernamePasswordAuthenticationToken authenticationToken =
				new UsernamePasswordAuthenticationToken(username, password);
		Authentication authentication = this.authManager.authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		User user = (User) this.userService.loadUserByUsername(username);
		user.setToken(tokenUtils.createToken(user));

		return user;
	}

}
