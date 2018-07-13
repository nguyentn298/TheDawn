package com.dante.security;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.dante.db.dawn.model.User;
import com.dante.db.dawn.model.UserRole;
import com.dante.db.dawn.repository.UserRepository;
import com.dante.db.dawn.repository.UserRoleRepository;

@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

	protected Logger log = Logger.getLogger(this.getClass());
	/**
	 * CAUTION: This class implemented UserDetailsService of spring frame work
	 * CustomUserDetailsService is only used if the AuthenticationManagerBuilder has not been populated and no AuthenticationProviderBean is defined
	 */

	@Autowired
	@Qualifier("userRepository")
	UserRepository userDao;
	
	@Autowired
	@Qualifier("userRoleRepository")
	UserRoleRepository userRoleDao;

	// @Transactional(readOnly=true)
	public UserDetails loadUserByUsername(final String userName) {

		UserDetails userDetails = null;
		try {
			User user = userDao.findByUserName(userName);
			
			if(user == null) {
				log.info("Invalid username or password.");
				return userDetails;
			}
			List<UserRole> listUserRoles = userRoleDao.findByUserUserId(user.getUserId());
			Set<UserRole> userRoles = new HashSet<UserRole>(listUserRoles);

			/**
			 * Build Authority (Spring) by role (Maybe we called it as role but spring called it as authority)
			 */
			List<GrantedAuthority> authorities = buildUserAuthority(userRoles);
			
			/**
			 * CATUION: Convert My User to Spring User
			 */
			userDetails = convertMyUserToSpringUser(user, authorities);
		}catch(Exception e) {
			log.error(String.format("User[%s] has problem", userName), e);
		}
		
		return userDetails;
	}


	public List<GrantedAuthority> buildUserAuthority(Set<UserRole> userRoles) {

		/**
		 * Use set to remove duplicate role then fill up a list below
		 */
		Set<GrantedAuthority> userAuthorities = new HashSet<GrantedAuthority>();

		// Build authorities for my user
		for (UserRole userRole : userRoles) {
			userAuthorities.add(new SimpleGrantedAuthority(userRole.getRole()));
		}

		List<GrantedAuthority> listUserAuthorities = new ArrayList<GrantedAuthority>(userAuthorities);
		return listUserAuthorities;
	}

	private org.springframework.security.core.userdetails.User convertMyUserToSpringUser(
			User user, List<GrantedAuthority> authorities) {

		// User(username, password, enabled, accountNonExpired,
		// credentialsNonExpired, accountNonLocked, authorities);
		return new org.springframework.security.core.userdetails.User(
				user.getUserName(), user.getPassword(), user.isEnable(), true,
				true, true, authorities);

	}
}
