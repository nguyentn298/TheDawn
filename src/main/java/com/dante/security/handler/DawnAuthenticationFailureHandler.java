package com.dante.security.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class DawnAuthenticationFailureHandler implements AuthenticationFailureHandler {

	static Logger log = Logger.getLogger(DawnAuthenticationFailureHandler.class);
	@Override
	public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			AuthenticationException authenticationException) {
		String userName = "";
		try {
			httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Bad credentials");
			userName = httpServletRequest.getParameter("username");
			httpServletResponse.sendRedirect("login?error=true");
		} catch(Exception e) {
			log.error(String.format("User[%s] can't login because [%s]", userName, authenticationException.getMessage()), e);
		}
	}
}
