package com.revolut.transfers.controller;

import static io.micronaut.http.HttpStatus.CREATED;

import com.revolut.transfers.model.AccountDto;
import com.revolut.transfers.model.NewAccountDto;
import com.revolut.transfers.service.AccountService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Status;
import io.micronaut.validation.Validated;
import java.math.BigDecimal;
import javax.inject.Inject;
import javax.validation.Valid;

@Validated
@Controller("/api/accounts")
public class AccountController {

  @Inject
  private AccountService accountService;

  @Post
  @Status(CREATED)
  public AccountDto create(@Valid NewAccountDto newAccount) {
    return accountService.create(newAccount);
  }

  @Get("/{id}")
  public AccountDto getById(String id) {
    AccountDto accountDto = new AccountDto();
    accountDto.setId(id);
    return accountDto;
  }

  @Get("/{id}/balance")
  public BigDecimal getBalanceOfAccount(String id) {
    return BigDecimal.ZERO;
  }
}