package com.trader.application.rest.resources.asm;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.trader.application.core.models.entities.Account;
import com.trader.application.rest.controllers.AccountController;
import com.trader.application.rest.resources.AccountResource;

public class AccountResourceAsm extends ResourceAssemblerSupport<Account, AccountResource> {

	public AccountResourceAsm() {
		super(AccountController.class, AccountResource.class);
	}

	@Override
	public AccountResource toResource(Account account) {
		AccountResource res = new AccountResource();
		res.setFirstName(account.getFirstName());
		res.setMiddleName(account.getMiddleName());
		res.setLastName(account.getLastName());
		res.setSchool(account.getSchool());
		res.setEmail(account.getEmail());
		res.setPassword(account.getPassword());

		res.add(linkTo(AccountController.class).slash(account.getId()).withSelfRel());

		return res;
	}

}
