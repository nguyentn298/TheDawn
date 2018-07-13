package com.dante.test.security;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.dante.db.dawn.service.TestService;
import com.dante.test.TestOperator;

public class TestLogin extends TestOperator {

	protected Logger log = Logger.getLogger(this.getClass());

	@Autowired
	TestService test;

	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;

	@Before
	public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(context)
				.apply(springSecurity())
				.build();
	}

	// Remove session in DawnAuthenticationSuccessHandler.java to test login
	@Test
	public void testLogin() {
		try {
			mvc.perform(formLogin("/login").user("nguyen").password("1"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testUnauthenticaion() {
		try {
			mvc
			.perform(formLogin().password("invalid"))
			.andExpect(unauthenticated());
		} catch (Exception e) {
			log.error(e);
		}
	}
}
