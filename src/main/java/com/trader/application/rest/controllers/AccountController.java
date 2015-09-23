package com.trader.application.rest.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.trader.application.core.models.entities.Account;
import com.trader.application.core.models.entities.utils.AccountListWrapper;
import com.trader.application.core.services.AccountService;
import com.trader.application.rest.exceptions.AccountExistsException;
import com.trader.application.rest.exceptions.ConflictException;
import com.trader.application.rest.resources.AccountListResource;
import com.trader.application.rest.resources.AccountResource;
import com.trader.application.rest.resources.asm.AccountListResourceAsm;
import com.trader.application.rest.resources.asm.AccountResourceAsm;

@RestController
@RequestMapping(ControllerMappingConstants.ACCOUNT_BASE_URL)
public class AccountController {

	//TODO: Make dynamic URI based on user id/name
	private static final String IMAGE_UPLOAD_DESTINATON = "C:/Users/CHUCHI/Desktop/db_deltas/";
	private AccountService accountService;

	@Autowired
	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}

	public AccountController() {

	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<AccountListResource> getAccounts() {
		AccountListWrapper allAccounts = accountService.findAll();

		AccountListResource res = new AccountListResourceAsm().toResource(allAccounts);
		return new ResponseEntity<AccountListResource>(res, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<AccountResource> createAccount(@RequestBody AccountResource sentAccount) {
		try {
			Account createdAccount = accountService.createAccount(sentAccount.toAccount());
			AccountResource res = new AccountResourceAsm().toResource(createdAccount);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(URI.create(res.getLink("self").getHref()));
			return new ResponseEntity<AccountResource>(res, headers, HttpStatus.CREATED);
		} catch (AccountExistsException exception) {
			throw new ConflictException(exception);
		}
	}

	@RequestMapping(value = "/{accountId}", method = RequestMethod.GET)
	public ResponseEntity<AccountResource> getAccount(@PathVariable Long accountId) {
		Account entity = accountService.find(accountId);
		if (entity != null) {
			AccountResource accountResource = new AccountResourceAsm().toResource(entity);
			return new ResponseEntity<AccountResource>(accountResource, HttpStatus.OK);
		} else {
			return new ResponseEntity<AccountResource>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/{accountId}/upload", method = RequestMethod.POST)
	public @ResponseBody String uploadImage(@PathVariable Long accountId,
			@RequestParam(value = "file") MultipartFile file) {
		String fileName = null;
		if (!file.isEmpty()) {
			try {
				fileName = file.getOriginalFilename();
				byte[] bytes = file.getBytes();
				BufferedOutputStream buffStream = new BufferedOutputStream(
						new FileOutputStream(new File(IMAGE_UPLOAD_DESTINATON + fileName)));
				buffStream.write(bytes);
				buffStream.close();
				return "You have successfully uploaded " + fileName;
			} catch (Exception e) {
				return "You failed to upload " + fileName + ": " + e.getMessage();
			}
		} else {
			return "Unable to upload. File is empty.";
		}
	}

	// @RequestMapping(value = "/{accountId}", method = RequestMethod.DELETE)
	// public ResponseEntity<AccountResource> deleteBlogEntry(@PathVariable Long
	// accountId) {
	// Account entry = accountService.delete(accountId);
	// if (entry != null) {
	// AccountResource res = new AccountResourceAsm().toResource(entry);
	// return new ResponseEntity<AccountResource>(res, HttpStatus.OK);
	// } else {
	// return new ResponseEntity<AccountResource>(HttpStatus.NOT_FOUND);
	// }
	// }
	//
	// @RequestMapping(value = "/{accountId}", method = RequestMethod.PUT)
	// public ResponseEntity<AccountResource> updateBlogEntry(@PathVariable Long
	// accountId,
	// @RequestBody AccountResource sentBlogEntry) {
	// Account updatedEntry = accountService.update(accountId,
	// sentBlogEntry.toAccount());
	// if (updatedEntry != null) {
	// AccountResource res = new AccountResourceAsm().toResource(updatedEntry);
	// return new ResponseEntity<AccountResource>(res, HttpStatus.OK);
	// } else {
	// return new ResponseEntity<AccountResource>(HttpStatus.NOT_FOUND);
	// }
	// }
}