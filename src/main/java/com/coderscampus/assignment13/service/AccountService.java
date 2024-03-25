package com.coderscampus.assignment13.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderscampus.assignment13.domain.Account;
import com.coderscampus.assignment13.domain.User;
import com.coderscampus.assignment13.repository.AccountRepository;

@Service
public class AccountService {

	@Autowired
	private AccountRepository accountRepo;

	public List<Account> findAll() {
		return accountRepo.findAll();
	}

	public Account saveAccount(Account account) {
		return accountRepo.save(account);
	}

	public Account findById(Long accountId) {
		Optional<Account> userOpt = accountRepo.findById(accountId);
		return userOpt.orElse(new Account());
	}

	public void delete(Long accountId) {
		accountRepo.deleteById(accountId);
	}
	
	public Integer findAccountIndex(List<Account> accounts, Long accountId) {
		for(int i = 0; i < accounts.size(); i++) {
			if(accounts.get(i).getAccountId().equals(accountId)){
				return i + 1;
			}
		}
		return 0;
	}
	
	public void nameNewBankAccount(Account account, User user) {
		Integer accountIndex = findAccountIndex(user.getAccounts(), account.getAccountId());
		account.setAccountName("Account # " + accountIndex);
		saveAccount(account);
	}

	public void createAndSaveNewBankAccount(Account account, User user) {
		account.getUsers().add(user);
		user.getAccounts().add(account);
		saveAccount(account);
	}
}