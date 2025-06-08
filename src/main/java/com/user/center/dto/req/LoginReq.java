package com.user.center.dto.req;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LoginReq {

	@NotBlank(message = "username is missing or blank")
	private String username;

	@NotBlank(message = "password is missing or blank")
	private String password;


}
