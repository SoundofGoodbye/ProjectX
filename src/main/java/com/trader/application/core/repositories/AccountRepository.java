package com.trader.application.core.repositories;

import java.util.List;

import com.trader.application.core.models.entities.Account;

public interface AccountRepository {
	public Account findAccount(Long id);

	public Account deleteAccount(Long id);

	public Account updateAccount(Long id, Account accountEntry);

	public Account createAccount(Account account);

	public Account findAccountByUsername(String username);

	public List<Account> findAll();
}
