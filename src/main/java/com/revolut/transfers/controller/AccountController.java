package com.revolut.transfers.controller;

import static io.micronaut.http.HttpStatus.CREATED;

import com.revolut.transfers.model.Account;
import com.revolut.transfers.model.NewAccountCommand;
import com.revolut.transfers.service.AccountService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Status;
import io.micronaut.validation.Validated;
import java.math.BigDecimal;
import java.util.List;
import javax.inject.Inject;
import javax.validation.Valid;

@Validated
@Controller("/api/accounts")
public class AccountController {

  @Inject
  private AccountService accountService;

  @Post
  @Status(CREATED)
  public Account create(@Valid NewAccountCommand newAccount) {
    return accountService.create(newAccount);
  }

  @Get
  public List<Account> getAll() {
    return accountService.getAll();
  }

  @Get("/{id}")
  public Account getById(final String id) {
    return accountService.getById(id);
  }

  @Get("/{id}/balance")
  public BigDecimal getAccountBalance(final String id) {
    return accountService.getBalance(id);
  }
}