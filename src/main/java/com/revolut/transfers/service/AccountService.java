package com.revolut.transfers.service;

import com.revolut.transfers.exception.AccountNotFoundException;
import com.revolut.transfers.model.Account;
import com.revolut.transfers.model.NewAccountDto;
import com.revolut.transfers.repository.AccountRepository;
import java.math.BigDecimal;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AccountService {

  @Inject
  private AccountRepository accountRepository;

  public Account create(NewAccountDto newAccountDto) {
    return accountRepository.create(newAccountDto);
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
