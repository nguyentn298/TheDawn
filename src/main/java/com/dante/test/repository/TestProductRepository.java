package com.dante.test.repository;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.dante.db.dawn2.model.Product;
import com.dante.db.dawn2.repository.ProductRepository;
import com.dante.test.TestOperator;

public class TestProductRepository extends TestOperator {

	@Autowired
	ProductRepository productRepository;

	@Test
	public void getAllProduct() {
		List<Product> list = productRepository.findAll();
		for (Product product : list) {
			System.out.println(product.getProductName());
		}
	}

	@Test
	public void getProductByNAme() {
		Product product = productRepository.findByProductName("test");
		System.out.println(product.getProductId());
		System.out.println(product.getProductName());
	}

}
