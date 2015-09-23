package com.trader.application.core.services;

import com.trader.application.core.models.entities.Account;
import com.trader.application.core.models.entities.utils.AccountListWrapper;

public interface AccountService {
	public Account find(Long id);

	public Account delete(Long id);

	public Account update(Long id, Account accountEntry);

	public Account createAccount(Account account);

	public AccountListWrapper findAll();

}
