package com.dante.db.dawn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dante.db.dawn.model.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
	
	List<UserRole> findByUserUserId(int userId);
}
