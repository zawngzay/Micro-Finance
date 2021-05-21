package com.mfi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.mfi.model.Permission;
import com.mfi.model.User;

public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserService userService;
	
	@Bean
	public UserDetailsService userDetailsService() throws Exception {
	    // ensure the passwords are encoded properly
	    UserBuilder users = org.springframework.security.core.userdetails.User.withDefaultPasswordEncoder();
	    InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
	    manager.createUser(users.username("user@gmail.com").password("password").roles("MASTER").build());
	    manager.createUser(users.username("admin").password("password").roles("USER","ADMIN").build());
	    return manager;
	}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.findByEmail(username);
		System.out.println("pass" + user.getPassword());
		System.out.println("enable" + user.getEnabled());
		for(Permission p :user.getPermission()) {
			System.out.println(p.getPerName());
		}
		
		user.getPermission();
		if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
            
        }
		
		return new MyUserDetails(user);
	}
}
