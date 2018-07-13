package com.dante.db.mongodb.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dante.db.mongodb.model.Person;

@Service
public class CrudWithMongoRepositoryService {

	@Autowired
	CrudWithMongoRepository crudWithMongoRepository;
	
	public List<Person> findAllPerson() {
		return crudWithMongoRepository.findAll();
	}
}
