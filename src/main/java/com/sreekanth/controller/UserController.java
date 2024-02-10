package com.sreekanth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sreekanth.model.User;
import com.sreekanth.service.UserService;

@RestController
@RequestMapping("api/v1")
public class UserController {

	@Autowired
	private UserService userServiceImpl;

	@GetMapping("/getUser")
	ResponseEntity<List<User>> getUser() {
		List<User> userList = userServiceImpl.getAllUsers();
		if (!userList.isEmpty()) {
			return new ResponseEntity<>(userList, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
		}

	}

	@PostMapping("/register")
	ResponseEntity<User> registerUser(@RequestBody User user) {
		User result = userServiceImpl.registerNewUser(user);

		return new ResponseEntity<>(result, HttpStatus.CREATED);

	}

	@PostMapping("/login")
	ResponseEntity<String> loginUser(@RequestBody User user) {
		String response;
	    boolean UserAuthenticated = userServiceImpl.authenticateUser(user);
		if (UserAuthenticated) {
	        response = "Logged in successfully";
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
	        response = "User details not found or login failed";
			return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
		}
	}

}
