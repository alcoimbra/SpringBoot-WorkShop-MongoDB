package br.com.capgemini.workshop.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.capgemini.workshop.domain.Post;

@Repository
public interface PostRepository extends MongoRepository<Post, String>{

}