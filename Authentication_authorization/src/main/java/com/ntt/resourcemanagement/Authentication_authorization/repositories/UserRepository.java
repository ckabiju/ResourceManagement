package com.ntt.resourcemanagement.Authentication_authorization.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ntt.resourcemanagement.Authentication_authorization.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

	User findByUserName(String userName);
}
