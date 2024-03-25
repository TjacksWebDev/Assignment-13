package com.coderscampus.assignment13.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.coderscampus.assignment13.domain.Account;
import com.coderscampus.assignment13.domain.User;
import com.coderscampus.assignment13.service.AccountService;
import com.coderscampus.assignment13.service.UserService;

@Controller
public class AccountController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AccountService accountService;
	
	
	@PostMapping("/users/{userId}/accounts")
	public String createBankAccount(@PathVariable Long userId, @ModelAttribute("account") Account account) {
		User user = userService.findById(userId);
		accountService.createAndSaveNewBankAccount(account, user);
		accountService.nameNewBankAccount(account, user);
		return "redirect:/users/" + userId + "/accounts/" + account.getAccountId();
	}

	@GetMapping("/users/{userId}/accounts/{accountId}")
	public String viewAccount(ModelMap model, @PathVariable Long userId, @PathVariable Long accountId) {
		User user = userService.findById(userId);
		Account account = accountService.findById(accountId);
		model.put("user", user);
		model.put("account", account);
		return "accounts";
	}

	@PostMapping("/users/{userId}/accounts/{accountId}") 
	public String updateAccount(@PathVariable Long userId, @PathVariable Long accountId, Account account) {
		accountService.saveAccount(account);
		return "redirect:/users/" + userId + "/accounts/" + accountId;
	}

}
