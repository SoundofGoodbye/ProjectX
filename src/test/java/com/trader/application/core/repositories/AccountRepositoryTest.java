package com.trader.application.core.repositories;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.trader.application.configuration.GlobalConfiguration;
import com.trader.application.core.models.entities.Account;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = GlobalConfiguration.class)
public class AccountRepositoryTest {

	@Autowired
	private AccountRepository accountRepo;

	private Account account;

	@Before
	@Transactional
	@Rollback(false)
	public void setUp() {
		account = new Account();
		account.setUsername("username");
		account.setPassword("password");
		account.setEmail("test@test.com");

		accountRepo.createAccount(account);
	}

	@Test
	@Transactional
	public void test() {
		Assert.notNull(accountRepo.findAccount(account.getId()));
	}
}
