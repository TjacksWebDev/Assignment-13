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
@RequestMapping("/users/{userId}/accounts")
public class AccountController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private AccountService accountService;
	
	@PostMapping("")
	public String createAccount (@PathVariable Long userId) {
		Account account = new Account();
		User user = userService.findById(userId);
		account.getUsers().add(user);
		user.getAccounts().add(account);
		account.setAccountName("Account #" + user.getAccounts().size());
		account = accountService.save(account);
		
		return "redirect:/users/"+userId+"/accounts/"+account.getAccountId();
	}
	
	@PostMapping("{accountId}")
	public String saveAccount(Account account, @PathVariable Long userId) {
		account = accountService.save(account);
		return "redirect:/users/"+userId+"/accounts/"+account.getAccountId();
	}
	
	@GetMapping("{accountId}")
	public String getAccount(ModelMap model, @PathVariable Long userId, @PathVariable Long accountId) {
		Account account = accountService.findById(accountId);
		User user = userService.findById(userId);
		model.put("account", account);
		model.put("user", user);
		return "account";
	}
}
