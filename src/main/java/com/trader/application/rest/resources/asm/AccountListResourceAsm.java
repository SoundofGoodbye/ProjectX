package com.trader.application.rest.resources.asm;

import java.util.List;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.trader.application.core.models.entities.utils.AccountListWrapper;
import com.trader.application.rest.controllers.AccountController;
import com.trader.application.rest.resources.AccountListResource;
import com.trader.application.rest.resources.AccountResource;

public class AccountListResourceAsm extends ResourceAssemblerSupport<AccountListWrapper, AccountListResource> {

	public AccountListResourceAsm() {
		super(AccountController.class, AccountListResource.class);
	}

	@Override
	public AccountListResource toResource(AccountListWrapper accountList) {
		List<AccountResource> resList = new AccountResourceAsm().toResources(accountList.getAccounts());
		AccountListResource finalRes = new AccountListResource();
		finalRes.setAccounts(resList);
		return finalRes;
	}
}