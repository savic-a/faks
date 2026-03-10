package iss.spring.security.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import iss.spring.security.entities.User;
import iss.spring.security.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository allUsers;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> ret = allUsers.findByUsername(username);
		if (!ret.isEmpty() ) {
			return org.springframework.security.core.userdetails.User.withUsername(username).password(ret.get().getPassword()).roles(ret.get().getRole().toString()).build();
		}
		throw new UsernameNotFoundException("User not found with this username: " + username);
	}

}
