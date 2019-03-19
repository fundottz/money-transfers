package com.revolut.transfers.controller;

import static io.micronaut.http.HttpRequest.POST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import com.revolut.transfers.model.Account;
import com.revolut.transfers.model.NewAccountCommand;
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

  @BeforeAll
  static void setupServer() {
    server = ApplicationContext.run(EmbeddedServer.class);
    client = server
        .getApplicationContext()
        .createBean(HttpClient.class, server.getURL()).toBlocking();
  }

  @Test
  void shouldCreateNewAccountAndReturnCreated() {
    var account = new NewAccountCommand();
    account.setBalance(BigDecimal.ZERO);

    var request = POST("/api/accounts", account);
    var createdAccount = client.exchange(request, Account.class);

    assertNotNull(createdAccount.body());
    assertNotNull(createdAccount.body().getId());
    assertEquals(HttpStatus.CREATED, createdAccount.status());
  }

  @Test
  void shouldNotCreateInvalidAccountAndReturnBadRequest() {
    var invalidAccount = new NewAccountCommand();
    invalidAccount.setBalance(BigDecimal.valueOf(-100));

    var request = POST("/api/accounts", invalidAccount);

    var clientException = assertThrows(
        HttpClientResponseException.class,
        () -> client.exchange(request, Account.class));
    assertEquals(HttpStatus.BAD_REQUEST, clientException.getResponse().status());
  }

  @Test
  void shouldReturnExistedAccount() {
    fail();
  }

  @Test
  void throwsNotFoundIfAccountNotExisted() {
    fail();
  }


  @Test
  void shouldReturnAccountBalanceIfAccountExisted() {
    fail();
  }

  @AfterAll
  static void stopServer() {
    if (server != null) {
      server.stop();
    }
  }
}