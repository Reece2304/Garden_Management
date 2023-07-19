package com.Horted.app.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Horted.app.Domain.Users;
import com.Horted.app.Repository.UserRepo;


@Service
public class UsersDetailsService implements UserDetailsService{

	@Autowired
	private UserRepo Urepo;
	
	@Override
	public UserDetails loadUserByUsername(String login)
			throws UsernameNotFoundException {
		
		Users domainUser = Urepo.findByUsername(login);
		
		//if user doesn't exist throw an error
		 if(domainUser == null){
		        throw new UsernameNotFoundException("User not authorized.");
		    }


		 //return the logged in user
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_"+ domainUser.getRole()));
	return new User(domainUser.getUsername(), domainUser.getPassword(), true, 
			true, true, true, authorities);
	}
	}
