package com.dante.test.repository;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.dante.db.mongodb.model.Person;
import com.dante.db.mongodb.repository.CrudWithMongoRepository;
import com.dante.db.mongodb.repository.CrudWithMongoRepositoryService;
import com.dante.test.TestOperator;

public class testMongoRepository extends TestOperator {
	
	@Autowired
	CrudWithMongoRepository crudWithMongoRepository;
	
	@Autowired
	CrudWithMongoRepositoryService crudWithMongoRepositoryService;

	/**
	 * Custom query
	 */
	@Test
	public void findOneByName() {
		Person person = crudWithMongoRepository.findOneByName("joe222");
		System.out.println(person);
	}

	@Test
	public void testInsertItem() {
		Person person = new Person("joe");
		crudWithMongoRepository.insert(person);
	}

	/**
	 * Create new row if not exist, update row if exist
	 */
	@Test
	public void testCreateOrUpdate() {
		Person person = crudWithMongoRepository.findOneByName("joe");
		person.setName("joe333");
		crudWithMongoRepository.save(person);
	}

	@Test
	public void testFindOneItem() {
		Person person = crudWithMongoRepository.findOne("5b2394db082887c1228948a6");
		System.out.println(person);
	}
	
	/**
	 * FindAll with Sort
	 */
	@Test
	public void testFindAllItem() {
		List<Person> persons = crudWithMongoRepository.findAll(new Sort(Sort.Direction.ASC, "name"));
		System.out.println(persons);
	}
	
	/**
	 * FindAll with Pageable
	 * The result in users list will be only one user: because of new PageRequest(0, 1);
	 */
	@Test
	public void testFindAllItemWithPageAble() {
		Pageable pageableRequest = new PageRequest(0, 1);
		Page<Person> page = crudWithMongoRepository.findAll(pageableRequest);
		List<Person> persons = page.getContent();
		System.out.println(persons);
	}

	@Test
	public void checkExist() {
		String personId = "5b2394db082887c1228948a6";
		boolean isExists = crudWithMongoRepository.exists(personId);
		System.out.println(isExists);
	}
	
	@Test
	public void testDelete() {
		Person person = new Person("TestRepository");
		crudWithMongoRepository.delete(person);
	}
	
}
