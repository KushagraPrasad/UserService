package com.example.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.user.dto.RegisterUserDto;
import com.example.user.entity.User;

@Service
public interface UserServices extends UserDetailsService {

	public User addUser(User user);

	@Transactional
	public boolean removeUserByMobile(String mobile);

	@Transactional
	public boolean removeUserByEmail(String email);

	public List<User> showAll();

	public User createAdministrator(RegisterUserDto input);

	public List<User> getUserM(String mobile);

	public Optional<User> getUserE(String email);

}
