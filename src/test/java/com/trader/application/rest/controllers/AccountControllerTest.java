package com.trader.application.rest.controllers;

import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.trader.application.core.models.entities.Account;
import com.trader.application.core.services.AccountService;
import com.trader.application.rest.exceptions.AccountExistsException;

public class AccountControllerTest {

	private static final String REQUEST_JSON = "{\"username\":\"test\",\"password\":\"test\",\"email\":\"test@test.com\"}";
	@InjectMocks
	private AccountController controller;

	@Mock
	private AccountService accountService;

	/**
	 * Used to perform rest request to specified URLs. It can be build to a full
	 * request (with headers,content etc) and then to validate the response,
	 * send back from the service.
	 */
	private MockMvc mockMvc;

	private ArgumentCaptor<Account> accountCaptor;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

		accountCaptor = ArgumentCaptor.forClass(Account.class);
	}

	@Test
	public void getExistingAccount() throws Exception {
		Account account = new Account();

		account.setId(1L);
		account.setUsername("Test user");
		account.setEmail("Test_user@test.com");
		account.setPassword("test");

		when(accountService.find(1L)).thenReturn(account);

		mockMvc.perform(get("/rest/accounts/1")).andDo(print())
				.andExpect(jsonPath("$.username", is(account.getUsername())))
				.andExpect(jsonPath("$.email", is(account.getEmail())))
				.andExpect(jsonPath("$.links[*].href", hasItem(endsWith("/accounts/1")))).andExpect(status().isOk());
	}

	@Test
	public void getNonExistingAccount() throws Exception {
		when(accountService.find(1L)).thenReturn(null);

		mockMvc.perform(get("/rest/accounts/1")).andExpect(status().isNotFound());
	}

	@Test
	public void createAccountNonExistingUsername() throws Exception {
		Account createdAccount = new Account();
		createdAccount.setId(1L);
		createdAccount.setPassword("test");
		createdAccount.setUsername("test");
		createdAccount.setEmail("test@test.com");

		when(accountService.createAccount(any(Account.class))).thenReturn(createdAccount);

		mockMvc.perform(post("/rest/accounts").content(REQUEST_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(header().string("Location", org.hamcrest.Matchers.endsWith("/rest/accounts/1")))
				.andExpect(jsonPath("$.username", is(createdAccount.getUsername()))).andExpect(status().isCreated());

		verify(accountService).createAccount(accountCaptor.capture());

		String password = accountCaptor.getValue().getPassword();
		assertEquals("test", password);
	}

	@Test
	public void createAccountExistingUsername() throws Exception {
		when(accountService.createAccount(any(Account.class))).thenThrow(new AccountExistsException());

		mockMvc.perform(post("/rest/accounts").content(REQUEST_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isConflict());

		verify(accountService).createAccount(accountCaptor.capture());
	}

}