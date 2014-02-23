package br.eti.mertz.springangular.application.repositories.jpa;

import javax.persistence.NoResultException;

import org.springframework.data.jpa.repository.JpaRepository;

import br.eti.mertz.springangular.application.domain.access.User;

public interface UserRepository extends JpaRepository<User, Long> {
	public User findByUsername(String username) throws NoResultException;
}