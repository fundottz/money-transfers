package com.revolut.transfers.service;

import com.revolut.transfers.exception.AccountNotFoundException;
import com.revolut.transfers.model.Account;
import com.revolut.transfers.model.CreateAccountCommand;
import com.revolut.transfers.repository.AccountRepository;
import java.math.BigDecimal;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AccountService {

  private final AccountRepository accountRepository;

  @Inject
  public AccountService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  public Account create(CreateAccountCommand newAccount) {
    return accountRepository.create(newAccount);
  }

  public Account getById(String id) {
    return accountRepository.getById(id)
        .orElseThrow(AccountNotFoundException::new);
  }

  public List<Account> getAll() {
    return accountRepository.getAll();
  }

  public BigDecimal getBalance(String id) {
    return accountRepository.getAccountBalance(id)
        .orElseThrow(AccountNotFoundException::new);
  }
}
