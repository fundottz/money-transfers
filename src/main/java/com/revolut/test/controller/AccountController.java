package com.revolut.test.controller;

import com.revolut.test.model.AccountDto;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/api/accounts")
public class AccountController {

  @Get("/{id}")
  public AccountDto getById(String id){
    AccountDto accountDto = new AccountDto();
    accountDto.setId(id);
    return accountDto;
  }
}