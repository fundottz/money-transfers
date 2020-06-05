package com.fundottz.transfers.controller;

import static io.micronaut.http.HttpRequest.GET;
import static io.micronaut.http.HttpRequest.POST;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fundottz.transfers.model.Account;
import com.fundottz.transfers.model.CreateAccountCommand;
import io.micronaut.context.ApplicationContext;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.BlockingHttpClient;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.runtime.server.EmbeddedServer;
import java.math.BigDecimal;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class AccountControllerTest {

  private static EmbeddedServer server;
  private static BlockingHttpClient client;

  private static String existedAccountId;

  @BeforeAll
  static void setupServer() {
    server = ApplicationContext.run(EmbeddedServer.class);
    client = server
        .getApplicationContext()
        .createBean(HttpClient.class, server.getURL()).toBlocking();

    CreateAccountCommand createAccountCommand = new CreateAccountCommand();
    createAccountCommand.setBalance(BigDecimal.TEN);
    var request = POST("/api/accounts", createAccountCommand);
    var createdAccount = client.exchange(request, Account.class);

    existedAccountId = createdAccount.body().getId();
  }

  @Test
  void shouldCreateAccountWithZeroBalanceAndReturnCreated() {
    var account = new CreateAccountCommand();
    account.setBalance(BigDecimal.ZERO);

    var request = POST("/api/accounts", account);
    var createdAccount = client.exchange(request, Account.class);

    assertNotNull(createdAccount.body());
    assertNotNull(createdAccount.body().getId());
    assertEquals(HttpStatus.CREATED, createdAccount.status());
  }

  @Test
  void shouldNotCreateAccountWithNegativeBalanceAndReturnBadRequest() {
    var invalidAccount = new CreateAccountCommand();
    invalidAccount.setBalance(BigDecimal.valueOf(-100));

    var request = POST("/api/accounts", invalidAccount);

    var clientException = assertThrows(
        HttpClientResponseException.class,
        () -> client.exchange(request, Account.class));
    assertEquals(HttpStatus.BAD_REQUEST, clientException.getResponse().status());
  }

  @Test
  void shouldNotCreateAccountWithoutBalanceAndReturnBadRequest() {
    var invalidAccount = new CreateAccountCommand();

    var request = POST("/api/accounts", invalidAccount);

    var clientException = assertThrows(
        HttpClientResponseException.class,
        () -> client.exchange(request, Account.class));
    assertEquals(HttpStatus.BAD_REQUEST, clientException.getResponse().status());
  }

  @Test
  void shouldReturnExistedAccount() {
    var request = GET("/api/accounts/" + existedAccountId);
    var existedAccount = client.exchange(request, Account.class).body();

    assertNotNull(existedAccount);
    assertEquals(existedAccountId, existedAccount.getId());
  }

  @Test
  void shouldReturnNotFoundIfAccountNotExisted() {
    var request = GET("/api/accounts/0");

    var clientException = assertThrows(
        HttpClientResponseException.class,
        () -> client.exchange(request, Account.class));
    assertEquals(HttpStatus.NOT_FOUND, clientException.getResponse().status());
  }

  @Test
  void shouldReturnAccountBalanceIfAccountExisted() {
    var request = GET(format("/api/accounts/%s/balance", existedAccountId));
    var balance = client.exchange(request, BigDecimal.class).body();

    assertNotNull(balance);
  }

  @AfterAll
  static void stopServer() {
    if (server != null) {
      server.stop();
    }
  }
}
