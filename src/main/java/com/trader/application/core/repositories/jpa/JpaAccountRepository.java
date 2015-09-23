package com.trader.application.core.repositories.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.trader.application.core.models.entities.Account;
import com.trader.application.core.repositories.AccountRepository;

/**
 * A single instance(bean) in memory that can be injectable.
 * 
 * @author CHUCHI
 *
 */
@Repository
public class JpaAccountRepository implements AccountRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Account deleteAccount(Long id) {
		Account accountToDelete = entityManager.find(Account.class, id);
		entityManager.remove(accountToDelete);
		return accountToDelete;
	}

	@Override
	public Account updateAccount(Long id, Account accountEntry) {
		Account account = entityManager.find(Account.class, id);
		account.setUsername(accountEntry.getUsername());
		account.setEmail(accountEntry.getEmail());
		account.setPassword(accountEntry.getPassword());

		return account;
	}

	@Override
	public Account createAccount(Account account) {
		entityManager.persist(account);

		return account;
	}

	@Override
	public Account findAccount(Long id) {

		return entityManager.find(Account.class, id);
	}

	@Override
	public Account findAccountByUsername(String username) {
		Query query = entityManager.createQuery("SELECT a FROM Account a WHERE a.username=?1");
		query.setParameter(1, username);

		@SuppressWarnings("unchecked")
		List<Account> accounts = query.getResultList();
		if (accounts.size() == 0) {
			return null;
		} else {
			return accounts.get(0);
		}
	}

	@Override
	public List<Account> findAll() {
		Query query = entityManager.createQuery("SELECT a FROM Account a");

		return query.getResultList();
	}

}
