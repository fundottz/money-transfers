package com.revolut.transfers.service;

import com.revolut.transfers.model.AccountDto;
import com.revolut.transfers.model.NewAccountDto;
import javax.inject.Singleton;

@Singleton
public class AccountService {

  public AccountDto create(NewAccountDto newAccountDto) {
    AccountDto accountDto = new AccountDto();
    accountDto.setBalance(newAccountDto.getBalance());
    accountDto.setId("asd");
    return accountDto;
  }
}
