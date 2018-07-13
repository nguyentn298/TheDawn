package com.dante.controller;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.dante.security.handler.DawnAuthenticationSuccessHandler.DawnAuthenticationException;

@Controller
@Transactional
public class LoginController {

	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public String defaultPage() {
		return "redirect:/login";

	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(@RequestParam(value = "error", required = false) String error,
//			@RequestParam(value = "logout", required = false) String logout, 
			HttpServletRequest request, Model model) {

		if (error != null) {
			model.addAttribute("error", getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
		}

//		if (logout != null) {
//			model.addAttribute("msg", "You've been logged out successfully.");
//		}
		
		return "register/login";

	}
	
//	@RequestMapping(value = "/logout", method = RequestMethod.GET)
//	public String logout(@RequestParam(value = "error", required = false) String error,
////			@RequestParam(value = "logout", required = false) String logout, 
//			HttpServletRequest request, Model model) {
//
//		if (error != null) {
//			model.addAttribute("error", getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
//		}
//
////		if (logout != null) {
////			model.addAttribute("msg", "You've been logged out successfully.");
////		}
//		
//		return "logout";
//
//	}

	@RequestMapping(value = "/welcome**", method = RequestMethod.GET)
	public String welcomePage(Model model, HttpServletRequest request) {
		model.addAttribute("title", "This is hello page!!");
		model.addAttribute("message", "Hello world");
		
		/**
		 * Test session
		 */
		HttpSession session = request.getSession(false);
		
		@SuppressWarnings("unchecked")
		Collection<GrantedAuthority> listUserAuthorities = (Collection<GrantedAuthority>) session.getAttribute("authorities");
		for (GrantedAuthority grantedAuthority : listUserAuthorities) {
			String role = grantedAuthority.getAuthority();
			if(role.equals("ROLE_ADMIN")) {
				model.addAttribute("adm", "I'm admin");
			}
		}
		return "hello";
	}

	@RequestMapping(value = "/admin**", method = RequestMethod.GET)
	public String adminPage(Model model) {

		model.addAttribute("title", "Admin page");
		model.addAttribute("message", "This page is for ROLE_ADMIN only!");

		return "/adminpage/admin";

	}

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public String userPage(Model model) {
		model.addAttribute("title", "User page");
		model.addAttribute("message", "This page is for ROLE_USER!");
		return "hello";
	}

	// for 403 access denied page
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String accesssDenied(Model model) {

		// check if user is login
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			System.out.println(userDetail);

			model.addAttribute("username", userDetail.getUsername());

		}

		return "403";

	}

	// customize the error message
	private String getErrorMessage(HttpServletRequest request, String key) {

		Exception exception = (Exception) request.getSession().getAttribute(key);

		String error = "";
		if (exception instanceof BadCredentialsException) {
			error = "Invalid username and password!";
		} else if (exception instanceof LockedException) {
			error = exception.getMessage();
		} else if (exception instanceof DawnAuthenticationException) {
			error = exception.getMessage();
		}else {
			error = "Invalid username and password!";
		}

		return error;
	}

}
