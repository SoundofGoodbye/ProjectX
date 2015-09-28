package com.trader.application.core.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trader.application.core.models.entities.Account;
import com.trader.application.core.models.entities.utils.AccountListWrapper;
import com.trader.application.core.repositories.AccountRepository;
import com.trader.application.core.services.AccountService;
import com.trader.application.core.services.exceptions.AccountDoesNotExistException;
import com.trader.application.rest.exceptions.AccountExistsException;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepo;

	@Override
	public Account find(Long id) {
		Account account = accountRepo.findAccount(id);
		if (account == null) {
			throw new AccountDoesNotExistException();
		}

		return account;
	}

	@Override
	public Account delete(Long id) {
		Account deleteAccount = accountRepo.deleteAccount(id);

		return deleteAccount;
	}

	@Override
	public Account update(Long id, Account accountEntry) {
		return accountRepo.updateAccount(id, accountEntry);
	}

	@Override
	public Account createAccount(Account account) {
		Account accountByUsername = accountRepo.findAccountByEmail(account.getEmail());
		if (accountByUsername != null) {
			throw new AccountExistsException();
		}

		return accountRepo.createAccount(account);
	}

	@Override
	public AccountListWrapper findAll() {
		return new AccountListWrapper(accountRepo.findAll());
	}

}
