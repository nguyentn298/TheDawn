package com.dante.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class DawnLogoutSuccessHandler implements LogoutSuccessHandler{

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
//		HttpSession session = request.getSession(false);
//		
//		System.out.println("Removed session: " + session.getId());
//		session.invalidate();
		response.setStatus(HttpServletResponse.SC_OK);
		response.sendRedirect("login");
	}

}
