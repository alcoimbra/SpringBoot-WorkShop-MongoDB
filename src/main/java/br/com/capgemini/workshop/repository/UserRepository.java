package br.com.capgemini.workshop.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.capgemini.workshop.domain.User;

@Repository
public interface UserRepository extends MongoRepository<User, String>{

}