package br.eti.mertz.springangular.application.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.eti.mertz.springangular.application.controllers.advice.ServerException;
import br.eti.mertz.springangular.application.domain.access.User;
import br.eti.mertz.springangular.application.repositories.jpa.UserRepository;
import br.eti.mertz.springangular.application.services.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	private UserRepository userRepository;
	
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	public UserServiceImpl(PasswordEncoder passwordEncoder,
			UserRepository userRepository) {
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
	}
	
	@Override
	public User save(User user) throws ServerException {
		
		if (user.getId() != null) {
			User u = this.userRepository.findOne(user.getId());
			
			if (!this.getCurrentUser().equals(u) && !AuthorityUtils.authorityListToSet(this.getCurrentUser().getAuthorities()).contains("ROLE_ADMINISTRATOR")) {
				throw new ServerException("You cannot edit another user");
			}
			
			if (user.getPasswordChange() != null && !StringUtils.isEmpty(user.getPasswordChange().getValue())) {
				if (!this.passwordEncoder.matches(user.getPasswordChange().getOlder(), u.getPassword())) {
					throw new ServerException("Old password is invalid");
				}
				if (!user.getPasswordChange().getValue().equals(user.getPasswordChange().getConfirm())) {
					throw new ServerException("Passwords don't match");
				}
				
				user.setPassword(passwordEncoder.encode(user.getPasswordChange().getValue()));
			} else {
				user.setPassword(u.getPassword());
			}
			user.setUsername(u.getUsername());
		} else {
			if (this.userRepository.findByUsername(user.getUsername()) != null) {
				throw new ServerException("Username already in use");
			}
			if (!user.getPassword().equals(user.getPasswordConfirm())) {
				throw new ServerException("Passwords don't match");
			}
			user.setPassword(passwordEncoder.encode(user.getPassword()));
		}
		User saved = this.userRepository.save(user);
		
		if (getCurrentUser().equals(saved)) {
			getCurrentUser().setName(saved.getName());
		}
		
		return saved;
	}
	
	@Override
	public void delete(Long userId) throws ServerException {
		User u = userRepository.findOne(userId);
		if (this.getCurrentUser().equals(u)) {
			throw new ServerException("You cannot delete yourself");
		}
		this.userRepository.delete(u);
	}
	
	@Override
	public User persist(User user) {
		String password = passwordEncoder.encode(user.getPassword());
		user.setPassword(password);
		user.setEnabled(true);
		return userRepository.save(user);
	}
	
	@Override
	public User update(User user) {
		User u = this.userRepository.findOne(user.getId());

		if (!u.getPassword().equals(user.getPassword())) {
			String password = this.passwordEncoder.encode(user.getPassword());
			user.setPassword(password);
		}

		return userRepository.save(user);
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void remove(User user) {
		if (user != null && user.getId() != null) {
			user = this.userRepository.findOne(user.getId());
			if (user != null) {
				this.userRepository.delete(user);
			}
		}
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) {
		User user = null;
			user = userRepository.findByUsername(username);
			if (user == null || user.getId() == null || user.getId() == 0){
				logger.error("Log in fail, username: " + username);
				throw new UsernameNotFoundException("User does not found");
			}
		return user;
	}
	
	@Override
	public User getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (auth != null)
			return (User) auth.getPrincipal();
		else
			return null;
	}
	
	@Override
	public User findById(Long id) {
		return this.userRepository.findOne(id);
	}
	
	@Override
	public long count() {
		return userRepository.count();
	}

	@Override
	public boolean checkCorrectPassword(User user, String password) {
		User u = this.userRepository.findOne(user.getId());
		return this.passwordEncoder.matches(password, u.getPassword());
	}
}