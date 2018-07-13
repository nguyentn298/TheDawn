package com.dante.filter;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class DawnBasicAuthenticationFilter extends BasicAuthenticationFilter {

	public DawnBasicAuthenticationFilter(AuthenticationManager authenticationManager,
			AuthenticationEntryPoint authenticationEntryPoint) {
		super(authenticationManager, authenticationEntryPoint);
		
		// TODO Auto-generated constructor stub
	}

}
