package com.sreekanth.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.sreekanth.model.User;
import com.sreekanth.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User registerNewUser(User user) {
		User userExists = userRepository.findByUsername(user.getUsername());
		if (!ObjectUtils.isEmpty(userExists)) {
			throw new RuntimeException("User already exists");
		}

		String encryptedPassword = encryptPassword(user.getPassword());
		user.setPassword(encryptedPassword);
		return userRepository.save(user);
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public User loadUserByUsername(String username) {
		return userRepository.findByUsername(username);

	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User getUser(User user) {
		User userData = userRepository.findByUsername(user.getUsername());
		String encoderPassword = encryptPassword(user.getPassword());
		if (encoderPassword.equalsIgnoreCase(userData.getPassword())) {
			return userData;
		} else {
			return null;
		}
	}

	private String encryptPassword(String password) {
		String encryptedpassword = null;
		try {
			MessageDigest m = MessageDigest.getInstance("MD5");
			m.update(password.getBytes());

			byte[] bytes = m.digest();

			StringBuilder s = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}

			encryptedpassword = s.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		/* Display the unencrypted and encrypted passwords. */
		System.out.println("Plain-text password: " + password);
		System.out.println("Encrypted password using MD5: " + encryptedpassword);
		return encryptedpassword;
	}

	@Override
	public boolean authenticateUser(User user) {
        User storedUser = userRepository.findByUsername(user.getUsername());
		String encoderPassword = encryptPassword(user.getPassword());
        if(encoderPassword.equalsIgnoreCase(storedUser.getPassword())) {
        	return true;
        }else {
        	return storedUser != null && passwordMatches(user.getPassword(), storedUser.getPassword());
        }
	}
	
    private boolean passwordMatches(String inputPassword, String storedPassword) {
        return inputPassword.equals(storedPassword);

    }
    
}
