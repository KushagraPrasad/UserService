package com.example.user.bootstrap;

import java.util.Optional;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.user.dto.RegisterUserDto;
import com.example.user.entity.Role;
import com.example.user.entity.RoleEnum;
import com.example.user.entity.User;
import com.example.user.repository.RoleRepository;
import com.example.user.repository.UserRepository;

@Component
public class AdminSeeder implements ApplicationListener<ContextRefreshedEvent> {
	private final RoleRepository roleRepository;
	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	public AdminSeeder(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.roleRepository = roleRepository;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		createSuperAdministrator();
	}

	private void createSuperAdministrator() {
		RegisterUserDto userDto = new RegisterUserDto();
		userDto.setName("Super Admin");
		userDto.setEmail("super.admin@email.com");
		userDto.setPassword("123456");
		userDto.setMobile("9867556423");

		Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.SUPER_ADMIN);
		Optional<User> optionalUser = userRepository.findByEmail(userDto.getEmail());

		if (optionalRole.isPresent() && !optionalUser.isPresent()) {
			Role role = optionalRole.get();
			User user = new User();
			user.setName(userDto.getName());
			user.setEmail(userDto.getEmail());
			user.setMobile(userDto.getMobile());
			user.setPassword(passwordEncoder.encode(userDto.getPassword()));
			user.setRole(role);

			userRepository.save(user);
		}
	}
}