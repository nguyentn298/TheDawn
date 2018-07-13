package com.dante.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@Component
public class DawnFilter extends GenericFilterBean {

	int count = 1;

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		System.out.println("Request: " + count);
		System.out.println("TheDawn is starting filter");
		// String session = servletRequest.
		try {
			System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
			System.out.println(SecurityContextHolder.getContext().getAuthentication().getCredentials());
			System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());

			System.out.println("TheDawn is Finishing filter");
			
		} catch (NullPointerException NPE) {
			System.out.println("Do nothing");
			
		}
		filterChain.doFilter(servletRequest, servletResponse);
		count++;
	}

}
