package iss.spring.security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import iss.spring.security.entities.User;
import iss.spring.security.security.jwt.JwtTokenUtil;

@RestController
@RequestMapping("/api/v2/login")
public class LoginController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@PostMapping()
	public User login(@RequestBody User user) {
		UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(user.getUsername(),
				user.getPassword());
		Authentication auth = authenticationManager.authenticate(authReq);

		SecurityContext sc = SecurityContextHolder.getContext();
		sc.setAuthentication(auth);

		String token = jwtTokenUtil.generateToken(user.getUsername());
		user.setJwt(token);

		return user;
	}

}
