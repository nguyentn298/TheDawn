package com.dante.db.dawn.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dante.db.dawn.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	User findByUserName(String userName);
}
