package com.cts.ecart.security;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cts.ecart.entity.User;
import com.cts.ecart.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository usersDao;

//	
//
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{

		Optional<User> user = usersDao.findByEmail(username);

		if (user.isPresent()) {
			log.info(user.get().getRole().name());
			List<SimpleGrantedAuthority> authorities = Collections
					.singletonList(new SimpleGrantedAuthority(user.get().getRole().name()));
			return new org.springframework.security.core.userdetails.User(user.get().getEmail(),
					user.get().getPassword(), authorities);
		}else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}
}
