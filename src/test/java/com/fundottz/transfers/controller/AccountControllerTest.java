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
  private static BlockingHttpClient httpClient;
  private static String existedAccountId;

  public static final String API_ROOT = "/api/accounts";

  @BeforeAll
  static void setupServer() {
    server = ApplicationContext.run(EmbeddedServer.class);
    httpClient = server.getApplicationContext()
        .createBean(HttpClient.class, server.getURL()).toBlocking();

    CreateAccountCommand createAccountCommand = new CreateAccountCommand(BigDecimal.TEN);
    var request = POST(API_ROOT, createAccountCommand);
    var createdAccount = httpClient.exchange(request, Account.class);

    existedAccountId = createdAccount.body().getId();
  }

  @Test
  void shouldCreateAccountWithZeroBalanceAndReturnCreated() {
    var account = new CreateAccountCommand(BigDecimal.ZERO);

    var request = POST(API_ROOT, account);
    var createdAccount = httpClient.exchange(request, Account.class);

    assertNotNull(createdAccount.body());
    assertNotNull(createdAccount.body().getId());
    assertEquals(HttpStatus.CREATED, createdAccount.status());
  }

  @Test
  void shouldNotCreateAccountWithNegativeBalanceAndReturnBadRequest() {
    var invalidAccount = new CreateAccountCommand(BigDecimal.valueOf(-100));

    var request = POST(API_ROOT, invalidAccount);

    var clientException = assertThrows(
        HttpClientResponseException.class,
        () -> httpClient.exchange(request, Account.class));
    assertEquals(HttpStatus.BAD_REQUEST, clientException.getResponse().status());
  }

  @Test
  void shouldNotCreateAccountWithoutBalanceAndReturnBadRequest() {
    var invalidAccount = new CreateAccountCommand();

    var request = POST(API_ROOT, invalidAccount);

    var clientException = assertThrows(
        HttpClientResponseException.class,
        () -> httpClient.exchange(request, Account.class));
    assertEquals(HttpStatus.BAD_REQUEST, clientException.getResponse().status());
  }

  @Test
  void shouldReturnExistedAccount() {
    var request = GET(API_ROOT + existedAccountId);
    var existedAccount = httpClient.exchange(request, Account.class).body();

    assertNotNull(existedAccount);
    assertEquals(existedAccountId, existedAccount.getId());
  }

  @Test
  void shouldReturnNotFoundIfAccountNotExisted() {
    var request = GET(format("%s/0", API_ROOT));

    var clientException = assertThrows(
        HttpClientResponseException.class,
        () -> httpClient.exchange(request, Account.class));
    assertEquals(HttpStatus.NOT_FOUND, clientException.getResponse().status());
  }

  @Test
  void shouldReturnAccountBalanceIfAccountExisted() {
    var request = GET(format("%s/%s/balance", API_ROOT, existedAccountId));
    var balance = httpClient.exchange(request, BigDecimal.class).body();

    assertNotNull(balance);
  }

  @AfterAll
  static void stopServer() {
    if (server != null) {
      server.stop();
    }
  }
}
