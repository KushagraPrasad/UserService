package com.example.user.dto;

public class LoginResponse {

	private String token;

	private long expiresIn;

	private String message;

	public LoginResponse(String token, long expiresIn) {
		this.token = token;
		this.expiresIn = expiresIn;
	}

	public LoginResponse() {
		// Default constructor
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getToken() {
		return token;
	}

	public long getExpiresIn() {
		return expiresIn;
	}

	public LoginResponse setExpiresIn(long expiresIn) {
		this.expiresIn = expiresIn;
		return this;
	}

	public LoginResponse setToken(String token) {
		this.token = token;
		return this;
	}
}
