package com.ntt.resourcemanagement.Authentication_authorization.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@Document(collection = "user") // Since we are using MongoDB, @Document annotation overrides the collection
								// user.
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class User {

	@Id
	private String id;
	private String firstName;
	private String lastName;
	private String email;
	private boolean confirmed;
	private String confirmationToken;
	private @NonNull String userName;
	private @NonNull String password;

}
