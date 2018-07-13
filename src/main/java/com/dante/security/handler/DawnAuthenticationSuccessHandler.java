package com.dante.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class DawnAuthenticationSuccessHandler implements AuthenticationSuccessHandler{
//	protected Logger log = Logger.getLogger(this.getClass());
	protected Logger log = Logger.getLogger(DawnAuthenticationSuccessHandler.class);

	@Override
	public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication auth)
			throws IOException, ServletException {
		User authUser = null;
		HttpSession session;
		try {
			authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			// request.getSession(false) will return current session if current session exists, then it will not create a new session.
			session = httpServletRequest.getSession(false);
			session.setAttribute("userName", authUser.getUsername());
			session.setAttribute("authorities", authUser.getAuthorities());
			
	        //set our response to OK status
	        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
	        log.info(String.format("Add session[%s] + to User[%s]", session.getId(), authUser.getUsername()));
	        // Redirect to welcome page

	        httpServletResponse.sendRedirect("welcome");
	        
	        
		} catch(Exception e){
			log.error(String.format("User[%s] has some problem for login", authUser.getUsername()), new DawnAuthenticationException("Test custome an exception (AuthenticationException)"));
		}

	}
	
	public class DawnAuthenticationException extends AuthenticationException {
		public DawnAuthenticationException(String msg) {
			super(msg);
		}
	}
	
	// Use to test login
//	@Override
//	public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication auth)
//			throws IOException, ServletException {
//		User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		System.out.println("Test login at DawnAuthenticationSuccessHandler: " + authUser.getUsername());
//		
//        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
// 
//        httpServletResponse.sendRedirect("welcome");
//	}

}
