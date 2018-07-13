package com.dante.db.mongodb.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.dante.db.mongodb.model.Person;

@Service
public class CrudWithMongoTemplate {

	@Autowired
	MongoTemplate mongoTemplate;

	public long countAllPersons() {
		return mongoTemplate.count(null, Person.class);
	}
	
	/**
	 * CRUD
	 */
	
	/**
	 *	Insert if not exist, Update if exist 
	 */
	public void createItem(Person person) {
		mongoTemplate.save(person);
	}
	
	/**
	 * Sample: 
		  Query query = new Query();
		  query.addCriteria(Criteria.where("name").is("joe"));
		  Update update = new Update();
		  update.set("name", "testUpdate");
	 */
	public List<Person> readFirstItem(Query query) {
		return mongoTemplate.find(query, Person.class);
	}

	public List<Person> readAllItem() {
		return mongoTemplate.findAll(Person.class);
	}

	public void updateFirstItem(Query query, Update update) {
		mongoTemplate.updateFirst(query, update, Person.class);
	}

	public void updateAllItem(Query query, Update update) {
		mongoTemplate.updateMulti(query, update, Person.class);
	}

	/**
	 *	Find and return object, then update 
	 */
	public Person findAndModify(Query query, Update update) {
		return mongoTemplate.findAndModify(query, update, Person.class);
	}

	/**
	 *	Update object and create it if not exist 
	 */
	public void updateOrCreate(Query query, Update update) {
		mongoTemplate.upsert(query, update, Person.class);
	}

	public void deleteItem(Query query) {
		mongoTemplate.remove(query, Person.class);
	}

}
