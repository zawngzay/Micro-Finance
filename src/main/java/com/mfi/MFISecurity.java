package com.mfi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mfi.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class MFISecurity extends WebSecurityConfigurerAdapter {

	// test
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsServiceImpl();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.inMemoryAuthentication().withUser("master@gmail.com").password("master").roles("MASTER");
		auth.authenticationProvider(authenticationProvider());

	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**", "/assets/**",
				"/fonts/**", "/dis/**", "/vendor1/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
		.antMatchers("/").permitAll().
		and()
		.authorizeRequests()
		.antMatchers("/mfi/**")
		.hasAnyAuthority("MASTER", "CHECKER", "Blacklist", "MAKER", "Account", "CRM", "COA", "Reports",
						"Transaction", "Loan","Admin")
		.anyRequest()
		.authenticated()
		.and()
		.formLogin()
		.permitAll()
		.loginPage("/login")
		.defaultSuccessUrl("/index")
		.failureUrl("/login?error=true")
		.and()
		.logout()
		.logoutSuccessUrl("/login")
		.invalidateHttpSession(true).deleteCookies("JSESSIONID")

		;
	}

}
