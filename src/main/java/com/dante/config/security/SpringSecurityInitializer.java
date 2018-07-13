package com.dante.config.security;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * In Java based configuration, you need to register the spring springSecurityFilterChain 
 * which is responsible for all security (protecting the application URLs, validating submitted username and passwords, 
 * redirecting to the log in form, etc) within your application.
 * @author Dante
 *
 */
public class SpringSecurityInitializer extends AbstractSecurityWebApplicationInitializer {

}