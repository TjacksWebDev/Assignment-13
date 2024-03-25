package com.coderscampus.assignment13.web;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.coderscampus.assignment13.domain.Account;
import com.coderscampus.assignment13.domain.Address;
import com.coderscampus.assignment13.domain.User;
import com.coderscampus.assignment13.service.AddressService;
import com.coderscampus.assignment13.service.UserService;


@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/users")
	public String getAllUsers(ModelMap model) {
		List<User> users = userService.findAll();
		model.put("users", users);
		return "users";
	}

	@GetMapping("/register")
	public String registerNewUser(ModelMap model) {
		model.put("user", new User());
		model.put("address", new Address());
		return "register";
	}

	@PostMapping("/register")
	public String createNewUser(User user, Address address) {
		userService.createUser(user, address);
		return "redirect:/users";
	}

	@GetMapping("/users/{userId}")
	public String viewUserByUserId(ModelMap model, @PathVariable Long userId) {
		User user = userService.findById(userId);
		Address address = user.getAddress();
		List<Account> accounts = user.getAccounts();
		model.put("user", user);
		model.put("address", address);
		model.put("accounts", accounts);
		return "update";
	}

	@PostMapping("/users/{userId}")
	public String updateUserInfo(@ModelAttribute("user") User updatedUser, Address address, String newPassword) {
		User existingUser = userService.findById(updatedUser.getUserId());
		userService.updateUserInfo(updatedUser, address, existingUser);
		userService.setNewPasswordIfExists(newPassword, existingUser);
		userService.saveUser(existingUser);
		return "redirect:/users/" + existingUser.getUserId();
	}


	@PostMapping("/users/{userId}/delete")
	public String deleteUser(@PathVariable Long userId) {
		userService.delete(userId);
		return "redirect:/users";
	}
}
