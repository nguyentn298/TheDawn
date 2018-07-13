package com.dante.db.dawn2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dante.db.dawn2.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{
	
	Product findByProductName(String productName);
}
