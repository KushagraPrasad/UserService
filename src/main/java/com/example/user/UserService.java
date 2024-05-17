package com.example.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.example.user")
@SpringBootApplication
public class UserService {

	public static void main(String[] args) {
		SpringApplication.run(UserService.class, args);
	}
}

