package com.dante.db.dawn.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class TestService {

	@PreAuthorize("hasRole('ADMIN')")
	public void printString() {
		System.out.println("This is a unit test on spring");
	}
}
