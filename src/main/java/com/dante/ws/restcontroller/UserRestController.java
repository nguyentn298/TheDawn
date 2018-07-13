package com.dante.ws.restcontroller;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dante.ws.demo.User;


@RestController
@RequestMapping("/rest")
public class UserRestController {
	
	@RequestMapping(value = "/testService")
	public String testRestController() {
		return "This is RestController (GET)";
	}
	
	@RequestMapping(value = "/testService2", method = RequestMethod.POST)
	@Produces({ MediaType.APPLICATION_XML })
	public User testRestController2() {
		return new User(1, "Irelia", "Warrior");
	}
	
	/**
	 * FIX ME 
	 * 415: The server refused this request 
	 * because the request entity is in a format not supported by the requested resource 
	 * for the requested method
	 */
	@PostMapping(value = "/testService3")
	public ResponseEntity testRestController3(@RequestBody String myId) {
//		return "This is RestController";
		System.out.println("myId");
		
		return new ResponseEntity(myId, HttpStatus.OK);
	}
}
