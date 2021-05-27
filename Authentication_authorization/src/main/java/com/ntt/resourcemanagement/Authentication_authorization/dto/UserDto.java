package com.ntt.resourcemanagement.Authentication_authorization.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class UserDto {

	private String id;
	private String firstName;
	private String lastName;
	private String email;
	private @NonNull String userName;
	private @NonNull String password;
}
