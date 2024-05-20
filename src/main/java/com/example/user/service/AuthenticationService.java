package com.example.user.service;

import java.util.NoSuchElementException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.example.user.dto.LoginUserDto;
import com.example.user.entity.User;
import com.example.user.repository.UserRepository;

@Service
public class AuthenticationService {

	private final UserRepository userRepository;

	private final AuthenticationManager authenticationManager;

	public AuthenticationService(UserRepository userRepository, AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
	}

	public User authenticate(LoginUserDto input) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword()));
		return userRepository.findByEmail(input.getEmail())
				.orElseThrow(() -> new NoSuchElementException("User not found"));
	}
}