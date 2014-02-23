package br.eti.mertz.springangular.application.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.eti.mertz.springangular.application.domain.access.User;

@Transactional(propagation=Propagation.REQUIRED)
public interface UserService extends UserDetailsService {
	public User persist(User user);
	public User update(User user);
	public void remove(User user);
	public User getCurrentUser();
	public User findById(Long id);
	public long count();
	public boolean checkCorrectPassword(User user, String password);
	public User save(User user);
	public void delete(Long userId);
}