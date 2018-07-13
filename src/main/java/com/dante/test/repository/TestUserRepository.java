package com.dante.test.repository;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.dante.db.dawn.model.User;
import com.dante.db.dawn.repository.UserRepository;
import com.dante.test.TestOperator;

public class TestUserRepository extends TestOperator {

	@Autowired
	UserRepository userRepository;

	@Test
	public void getAllUser() {
		List<User> list = userRepository.findAll();
		for (User user : list) {
			System.out.println(user.getUserName());
		}
	}

	@Test
	public void getUserByNAme() {
		User user = userRepository.findByUserName("test");
		System.out.println(user.getUserName());
		System.out.println(user.getPassword());
	}

}
