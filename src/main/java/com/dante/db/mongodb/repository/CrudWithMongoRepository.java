package com.dante.db.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.dante.db.mongodb.model.Person;

public interface CrudWithMongoRepository extends MongoRepository<Person, String>{

	 public Person findOneByName(String name);
}
