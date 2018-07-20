package com.dante.test.repository;

import java.net.UnknownHostException;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.dante.db.mongodb.model.Person;
import com.dante.db.mongodb.repository.CrudWithMongoTemplate;
import com.dante.test.TestOperator;
import com.mongodb.MongoClient;

public class TestMongoTemplate extends TestOperator {

	static Logger log = Logger.getLogger(TestMongoTemplate.class);

	@Autowired
	CrudWithMongoTemplate crudWithMongoTemplate;

	Query query;
	Update update;

	@Before
	public void buildQueryAndUpdate() {
		System.out.println("Start Crud with Mongo Template");
		query = new Query();
		update = new Update();
	}
	
	@After
	public void closeTest() {
		System.out.println("Finish Crud with Mongo Template");
	}

	@Test
	public void countAllItem() {
		System.out.println("Number of Item: " + crudWithMongoTemplate.countAllPersons());
	}

	@Test
	public void testInsertItem() {
		Person person = new Person("nguyen222");
		crudWithMongoTemplate.createItem(person);
		log.info("Insert: " + person);
	}

	@Test
	public void testFindItem() {
		query.addCriteria(Criteria.where("name").is("dante"));
		List<Person> person = crudWithMongoTemplate.readFirstItem(query);
		System.out.println(person);
	}

	@Test
	public void testFindAll() {
		List<Person> list = crudWithMongoTemplate.readAllItem();
		for (Person person : list) {
			System.out.println(person);
		}
	}

	@Test
	public void testUpdateFirstItem() {
		query.addCriteria(Criteria.where("name").is("testUpdate"));
		update.set("name", "testDelete");
		
		crudWithMongoTemplate.updateFirstItem(query, update);
	}

	@Test
	public void testUpdateAllItem() {
		query.addCriteria(Criteria.where("name").is("Joe"));
		update.set("name", "testUpdate");
		
		crudWithMongoTemplate.updateAllItem(query, update);
	}

	@Test
	public void testFindAndModify() {
		query.addCriteria(Criteria.where("name").is("nguyen22"));
		update.set("name", "testUpdate22");
		Person person = crudWithMongoTemplate.findAndModify(query, update);
		System.out.println(person);
	}

	@Test
	public void testUpdateOrCreate() {
		query.addCriteria(Criteria.where("name").is("hello"));
		update.set("name", "testUpdate22");
		crudWithMongoTemplate.updateOrCreate(query, update);
	}

	@Test
	public void testDeleteItem() {
		query.addCriteria(Criteria.where("name").is("testDelete"));
		crudWithMongoTemplate.deleteItem(query);
	}

	/**
	 *	Another way to use mongoTemplate without config 
	 */
	public static void testInsert() throws UnknownHostException {
		MongoOperations mongoOps = new MongoTemplate(new SimpleMongoDbFactory(new MongoClient(), "test"));
		List<Person> list = mongoOps.findAll(Person.class);
		System.out.println(list);
	}

	public static void main(String[] args) throws UnknownHostException {
		testInsert();
	}

}
