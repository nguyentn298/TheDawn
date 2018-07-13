package com.dante.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class CheckBean extends TestOperator {

	@Autowired
	ApplicationContext applicationContext;

	@Test
	public void checkBean() {

		String[] myBeans = applicationContext.getBeanDefinitionNames();
		for (String beanName : myBeans) {
			System.out.println("My bean ====> " + beanName);
			// System.out.println(beanName + " : " +
			// applicationContext.getBean(beanName).getClass().toString());
		}

		((AbstractApplicationContext) applicationContext).close();
	}
}
