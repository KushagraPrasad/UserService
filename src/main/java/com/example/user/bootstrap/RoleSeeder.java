package com.example.user.bootstrap;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.example.user.entity.Role;
import com.example.user.entity.RoleEnum;
import com.example.user.repository.RoleRepository;

@Component
public class RoleSeeder implements ApplicationListener<ContextRefreshedEvent> {
	private final RoleRepository roleRepository;

	public RoleSeeder(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		this.loadRoles();
	}

	private void loadRoles() {
		RoleEnum[] roleNames = { RoleEnum.USER, RoleEnum.ADMIN, RoleEnum.SUPER_ADMIN };
		Map<RoleEnum, String> roleDescriptionMap = new HashMap<>();
		roleDescriptionMap.put(RoleEnum.USER, "Default user role");
		roleDescriptionMap.put(RoleEnum.ADMIN, "Administrator role");
		roleDescriptionMap.put(RoleEnum.SUPER_ADMIN, "Super Administrator role");

		Arrays.stream(roleNames).forEach(roleName -> {
			Optional<Role> optionalRole = roleRepository.findByName(roleName);
			if (optionalRole.isPresent()) {
				System.out.println(optionalRole.get());
			} else {
				Role roleToCreate = new Role();
				roleToCreate.setName(roleName);
				roleToCreate.setDescription(roleDescriptionMap.get(roleName));
				roleRepository.save(roleToCreate);
			}
		});
	}
}