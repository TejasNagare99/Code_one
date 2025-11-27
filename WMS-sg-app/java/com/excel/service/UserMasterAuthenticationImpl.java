package com.excel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.excel.model.Usermaster;
import com.excel.repository.UserMasterRepository;
import com.excel.utility.CodifyErrors;

@Service
public class UserMasterAuthenticationImpl implements UserDetailsService {

	private static final String USER = "USER";
	@Autowired
	private UserMasterRepository userMasterRepository;


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserBuilder builder = null;
		try {
			Usermaster user = this.userMasterRepository.getUserByUsername(username);
			if (user != null) {
				builder = User.withUsername(username);
				builder.password(user.getLd_pass());
				builder.roles(USER);
			}
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return builder.build();
	}
	 

	/*
	 * @Override public UserDetails loadUserByUsername(String username) throws
	 * UsernameNotFoundException { UserBuilder builder = null; try { builder =
	 * User.withUsername(username); builder.password(
	 * "AIil+pHRWrpUA8ce0CC3zw==:C5NCDTXWe+WuvdcA51INkmyDLnI4YsUeDDGuxma5O/HZuuDk5ecpfHSvsgRm0uXSiN2y4tiNv+ERviw4SKuFnA=="
	 * ); builder.roles(USER); } catch (Exception e) { System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --; } return
	 * builder.build(); }
	 */
}
