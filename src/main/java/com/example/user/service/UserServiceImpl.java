package com.example.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.user.dto.RegisterUserDto;
import com.example.user.entity.Role;
import com.example.user.entity.RoleEnum;
import com.example.user.entity.User;
import com.example.user.repository.RoleRepository;
import com.example.user.repository.UserRepository;

@Service
public class UserServiceImpl implements UserServices {

	private final UserRepository userRepository;

	private final RoleRepository roleRepository;

	private final PasswordEncoder passwordEncoder;

	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
			PasswordEncoder passwordEncoder) {
		super();
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public User addUser(User user) throws RuntimeException {
		List<User> existingUserByEmail = userRepository.findByEmailAndName(user.getEmail(), user.getName());
		List<User> existingUserByMobile = userRepository.findByMobileAndName(user.getMobile(), user.getName());

		if (!existingUserByEmail.isEmpty() || !existingUserByMobile.isEmpty()) {
			throw new RuntimeException("User already exists");
		} else {
			return userRepository.save(user);
		}
	}

	public User createAdministrator(RegisterUserDto input) {
		return roleRepository.findByName(RoleEnum.ADMIN).map((Role role) -> {
			User user = new User();
			user.setName(input.getName());
			user.setEmail(input.getEmail());
			user.setMobile(input.getMobile());
			user.setPassword(passwordEncoder.encode(input.getPassword()));
			user.setRole(role);
			return user;
		}).map((User user) -> userRepository.save(user)).orElse(null);
	}

	public boolean removeUserByMobile(String mobile) {
		List<User> users = userRepository.findByMobile(mobile);
		if (!users.isEmpty()) {
			userRepository.deleteByMobile(mobile);
			return true;
		}
		return false;
	}

	/*
	 * public boolean removeUserByEmail(String email) { List<User> user =
	 * userRepository.findByEmail(email); if (!user.isEmpty()) {
	 * userRepository.deleteByEmail(email); return true; // User found and deleted }
	 * return false; // User not found }
	 */

	public List<User> getUserM(String mobile) {
		if (userRepository.findByMobile(mobile) != null) {
			List<User> user = userRepository.findByMobile(mobile);
			return user;
		}
		return null;
	}

	public Optional<User> getUserE(String email) {
		if (userRepository.findByEmail(email) != null) {
			Optional<User> user = userRepository.findByEmail(email);
			return user;
		}
		return null;
	}

	public List<User> showAll() {
		return userRepository.findAll();
	}

	public User updateUser(User user, String id) {

		Optional<User> existingUserOptional = userRepository.findById(id);

		if (existingUserOptional.isPresent()) {
			// Update the existing users fields with the new values
			User existingUser = existingUserOptional.get();
			existingUser.setName(user.getName());
			existingUser.setEmail(user.getEmail());
			existingUser.setMobile(user.getMobile());
			return userRepository.save(existingUser);
		} else {
			throw new RuntimeException("Entered invalid user id, please enter a valid user id.");
		}
	}

	@Override
	public boolean removeUserByEmail(String email) {
		// TODO Auto-generated method stub
		return false;
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
