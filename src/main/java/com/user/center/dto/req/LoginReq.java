package com.user.center.dto.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginReq {

	@NotBlank(message = "username is missing or blank")
	private String username;

	@NotBlank(message = "password is missing or blank")
	private String password;


}
