package br.eti.mertz.springangular.application.repositories.mongo;

import javax.persistence.NoResultException;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.eti.mertz.springangular.application.domain.access.User;

@Repository("userRepositoryMongo")
public interface UserRepository extends MongoRepository<User, String> {
	public User findByUsername(String username) throws NoResultException;

}
