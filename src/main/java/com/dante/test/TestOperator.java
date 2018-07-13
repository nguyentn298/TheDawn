package com.dante.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.dante.config.WebAppContext;

@RunWith(SpringJUnit4ClassRunner.class) 				// Use for junit on spring
@ContextConfiguration(classes = {WebAppContext.class}) 	// use to load context
@WebAppConfiguration 									// used at class level to integrate test that declares ApplicationContext
public class TestOperator {

}
