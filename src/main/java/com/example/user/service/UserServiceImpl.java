package com.example.user.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.user.entity.User;
import com.example.user.repository.RoleRepository;
import com.example.user.repository.UserRepository;

@Service
public class UserServiceImpl implements UserServices {

	private final UserRepository userRepository;

	private final RoleRepository roleRepository;

	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
		super();
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> userOptional = userRepository.findByEmail(username); // Assuming username is email
		User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("User not found"));

		// Build and return UserDetails object based on the user entity
		return org.springframework.security.core.userdetails.User.builder().username(user.getEmail()) // Assuming email
																										// is used as
																										// username
				.password(user.getPassword()) // Assuming the password is stored securely (e.g., hashed)
				.roles(user.getRole().getName().toString()) // Assuming user has a single role
				.build();
	}
}
