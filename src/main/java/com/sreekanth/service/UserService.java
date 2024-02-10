package com.sreekanth.service;

import java.util.List;

import com.sreekanth.model.User;

public interface UserService {
	
	User registerNewUser(User user);

    List<User> findAll();

	List<User> getAllUsers();

	User getUser(User user);

	User loadUserByUsername(String username);

	boolean authenticateUser(User user);

}
