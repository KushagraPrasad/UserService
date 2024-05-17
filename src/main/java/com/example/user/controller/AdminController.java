package com.example.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.user.dto.RegisterUserDto;
import com.example.user.entity.User;
import com.example.user.service.UserServices;

@RequestMapping("/admins")
@RestController
public class AdminController {

	private final UserServices userService;

	public AdminController(UserServices userService) {
		this.userService = userService;
	}

	@PostMapping
	@PreAuthorize("hasRole('SUPER_ADMIN')")
	public ResponseEntity<User> createAdministrator(@RequestBody RegisterUserDto registerUserDto) {
		User createdAdmin = userService.createAdministrator(registerUserDto);
		return ResponseEntity.ok(createdAdmin);
	}
}