package com.fundottz.transfers.controller;

import static io.micronaut.http.HttpStatus.CREATED;

import com.fundottz.transfers.model.Account;
import com.fundottz.transfers.model.CreateAccountCommand;
import com.fundottz.transfers.service.AccountService;
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

  private final AccountService accountService;

  @Inject
  public AccountController(AccountService accountService) {
    this.accountService = accountService;
  }

  @Post
  @Status(CREATED)
  public Account create(@Valid CreateAccountCommand newAccount) {
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
