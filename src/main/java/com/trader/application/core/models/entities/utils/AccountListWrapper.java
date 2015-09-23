package com.trader.application.core.models.entities.utils;

import java.util.List;

import com.trader.application.core.models.entities.Account;

public class AccountListWrapper {
	private List<Account> accounts;

	public AccountListWrapper(List<Account> list) {
		this.accounts = list;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}
}
