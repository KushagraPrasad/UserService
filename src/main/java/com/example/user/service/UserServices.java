package com.example.user.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.example.user.entity.User;

@Service
public interface UserServices extends UserDetailsService {

	List<User> showAll();

}
