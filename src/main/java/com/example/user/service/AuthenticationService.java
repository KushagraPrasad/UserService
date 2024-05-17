package com.example.user.service;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.user.dto.LoginUserDto;
import com.example.user.dto.RegisterUserDto;
import com.example.user.entity.Role;
import com.example.user.entity.RoleEnum;
import com.example.user.entity.User;
import com.example.user.repository.RoleRepository;
import com.example.user.repository.UserRepository;

@Service
public class AuthenticationService {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	private final AuthenticationManager authenticationManager;

	private final RoleRepository roleRepository;

	public AuthenticationService(UserRepository userRepository, AuthenticationManager authenticationManager,
			PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.roleRepository = roleRepository;
	}

	public User signup(RegisterUserDto input) {
		Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.USER);

		return optionalRole.map((Role role) -> new User().setName(input.getName()).setEmail(input.getEmail())
				.setPassword(passwordEncoder.encode(input.getPassword())).setRole(role).setMobile(input.getMobile()))
				.map((User user) -> userRepository.save(user)).orElse(null);
	}

	public User authenticate(LoginUserDto input) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword()));
		return userRepository.findByEmail(input.getEmail())
				.orElseThrow(() -> new NoSuchElementException("User not found"));
	}
}