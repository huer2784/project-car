package com.packt.cardatabase.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.packt.cardatabase.domain.AppUser;
import com.packt.cardatabase.domain.AppUserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	private final AppUserRepository appUserRepository;
	
	public UserDetailsServiceImpl(AppUserRepository appUserRepository) {
		this.appUserRepository = appUserRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<AppUser> user = appUserRepository.findByUsername(username);
		
		UserBuilder builder = null;
		
		if(user.isPresent()) {
			AppUser loginUser = user.get();
			
			builder = User.withUsername(username);
			builder.password(loginUser.getPassword());
			builder.roles(loginUser.getRole());
			
		}else {
			throw new UsernameNotFoundException("User not found");
		}
		
		return builder.build();
	}

}
