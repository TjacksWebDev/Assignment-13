package com.coderscampus.assignment13.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderscampus.assignment13.domain.Address;
import com.coderscampus.assignment13.domain.User;
import com.coderscampus.assignment13.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;

	public List<User> findAll() {
		return userRepo.findAll();
	}

	public User saveUser(User user) {
		return userRepo.save(user);
	}

	public User findById(Long userId) {
		Optional<User> userOpt = userRepo.findById(userId);
		return userOpt.orElse(new User());
	}

	public void delete(Long userId) {
		userRepo.deleteById(userId);
	}
	public void updateUserInfo(User updatedUser, Address address, User existingUser) {
		existingUser.setName(updatedUser.getName());
		existingUser.setUsername(updatedUser.getUsername());
		existingUser.setAddress(address);
		existingUser.getAccounts().addAll(updatedUser.getAccounts());
	}
	
	public void setNewPasswordIfExists(String newPassword, User existingUser) {
		if (newPassword != null && !newPassword.isEmpty()) {
			 existingUser.setPassword(newPassword);
		 }
	}
	
	public void createUser(User user, Address address) {
		address.setUser(user);
		user.setAddress(address);
		saveUser(user);
	}
}