package com.bootcamp.capstone.prs.user;

import java.util.*;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
	Optional<User> findByUsernameAndPassword(String username, String password);

}
