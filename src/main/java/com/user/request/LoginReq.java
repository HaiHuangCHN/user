package com.user.request;

import javax.validation.constraints.NotBlank;

public class LoginReq {

	@NotBlank(message = "username is missing or blank")
	private String username;
	@NotBlank(message = "password is missing or blank")
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "LoginReq [username=" + username + ", password=" + password + "]";
	}
}
