package com.dante.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

import com.dante.filter.DawnFilter;
import com.dante.security.handler.DawnAuthenticationFailureHandler;
import com.dante.security.handler.DawnAuthenticationSuccessHandler;
import com.dante.security.handler.DawnLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
@Transactional
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("userDetailsService")
	UserDetailsService userDetailsService;

	@Autowired
	DawnAuthenticationSuccessHandler dawnAuthenticationSuccessHandler;

	@Autowired
	DawnAuthenticationFailureHandler dawnAuthenticationFailureHandler;

	@Autowired
	DawnLogoutSuccessHandler dawnLogoutSuccessHandler;

	@Autowired
	DawnFilter dawnFilter;
	
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

		// Users in memory.

		// auth.inMemoryAuthentication().withUser("user1").password("12345").roles("ROLE_USER");
		// auth.inMemoryAuthentication().withUser("admin1").password("12345").roles("ROLE_USER,
		// ROLE_ADMIN");

		auth.inMemoryAuthentication().withUser("ram").password("ram123").roles("ADMIN");
		// For User in database, CustomUserDetailsService.java implemented interface userDetailsService
		auth.userDetailsService(userDetailsService);

	}

	/**
	 * IMPORTANT: USE method = POST to login and logout, if method = GET,
	 * logoutSuccessHandler is not work
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		/**
		 * Do you want to meet a csrf attack?
		 * If enable csrf(), Rest API cant work with Post method
		 * 
		 * When you use CSRF protection? Our recommendation is to use CSRF protection for any request 
		 * that could be processed by a browser by normal users. 
		 * If you are only creating a service that is used by non-browser clients, 
		 * you will likely want to disable CSRF protection.
		 */
//		 http.csrf().disable();

		/**
		 * Test AddFilter
		 */
//		http.addFilterBefore(dawnFilter, BasicAuthenticationFilter.class);
		
		/**
		 * The pages does not require login
		 */
		http.authorizeRequests().antMatchers("/", "/welcome", "/rest").permitAll();
		/**
		 * The pages require login userInfo page requires login as USER or ADMIN. If no
		 * login, it will redirect to /login page.
		 */
		http.authorizeRequests().antMatchers("/user").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')");

		/**
		 * Pages for admin role
		 */
		http.authorizeRequests().antMatchers("/admin").access("hasRole('ROLE_ADMIN')");

		/**
		 * When the user has logged in as XX. But access a page that requires role YY,
		 * AccessDeniedException will throw.
		 */
		http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");

		// Config for Login Form
		http.authorizeRequests().and().formLogin()
		
				// Submit URL of login page.
//				.loginProcessingUrl("/dante_spring_security_check")
				.loginProcessingUrl("/login")
				// Submit URL
				.loginPage("/login")
				
				// just one success (choose 1 between successHandler or defaultSuccessUrl)
				.successHandler(dawnAuthenticationSuccessHandler)
				// .defaultSuccessUrl("/welcome")
				
				// just one failure (choose 1 between failureHandler or failureUrl)
				.failureHandler(dawnAuthenticationFailureHandler)
				// .failureUrl("/login?error=true")
				
				.usernameParameter("username")
				.passwordParameter("password");

		// Config for Logout Page (choose 1 between logoutSuccessHandler or logoutSuccessUrl)
		http.authorizeRequests().and().logout().logoutUrl("/logoutSuccess").logoutSuccessHandler(dawnLogoutSuccessHandler)
		// .logoutUrl("/logout").logoutSuccessUrl("/login")
		;

	}

	// @Override
	// protected void configure(AuthenticationManagerBuilder auth) throws Exception
	// {
	// auth.authenticationProvider(authProvider);
	//
	// // Users in memory.
	//
	// auth.inMemoryAuthentication().withUser("user1").password("12345").roles("USER");
	// auth.inMemoryAuthentication().withUser("admin1").password("12345").roles("USER,
	// ADMIN");
	//
	// // For User in database.
	//// auth.userDetailsService(userDetailsService);
	// }
	// @Bean
	// DefaultAuthenticationEventPublisher defaultAuthenticationEventPublisher() {
	// return new DefaultAuthenticationEventPublisher();
	// }
	// @Autowired
	// public void configureGlobal(AuthenticationManagerBuilder auth) throws
	// Exception {
	// auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	// }
	//
	// @Override
	// protected void configure(HttpSecurity http) throws Exception {
	// http.authorizeRequests().antMatchers("/admin/**")
	// .access("hasRole('ROLE_ADMIN')")
	// .and().formLogin().loginPage("/login").failureUrl("/login?error")
	// .usernameParameter("username")
	// .passwordParameter("password")
	// .and().logout().logoutSuccessUrl("/login?logout")
	// .and().csrf()
	// .and().exceptionHandling().accessDeniedPage("/403");
	//
	// }
	//
	// @Bean
	// public PasswordEncoder passwordEncoder(){
	// PasswordEncoder encoder = new BCryptPasswordEncoder();
	// return encoder;
}
