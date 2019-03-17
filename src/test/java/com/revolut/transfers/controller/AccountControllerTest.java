package com.revolut.transfers.controller;

import static io.micronaut.http.HttpRequest.POST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.revolut.transfers.model.AccountDto;
import com.revolut.transfers.model.NewAccountDto;
import io.micronaut.context.ApplicationContext;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
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
    NewAccountDto account = new NewAccountDto();
    account.setBalance(BigDecimal.ZERO);

    HttpRequest<NewAccountDto> request = POST("/api/accounts", account);
    HttpResponse<AccountDto> createdAccount = client.exchange(request, AccountDto.class);

    assertNotNull(createdAccount.body());
    assertNotNull(createdAccount.body().getId());
    assertEquals(HttpStatus.CREATED, createdAccount.status());
  }

  @Test
  void shouldNotCreateInvalidAccountAndReturnBadRequest() {
    NewAccountDto invalidAccount = new NewAccountDto();
    invalidAccount.setBalance(BigDecimal.valueOf(-100));

    HttpRequest<NewAccountDto> request = POST("/api/accounts", invalidAccount);

    HttpClientResponseException clientException = assertThrows(
        HttpClientResponseException.class,
        () -> client.exchange(request, AccountDto.class));

    assertEquals(HttpStatus.BAD_REQUEST, clientException.getResponse().status());
  }

  @Test
  void shouldReturnExistedAccount() {
    assertFalse(true);
  }

  @Test
  void throwsNotFoundIfAccountNotExisted() {
    assertFalse(true);
  }


  @Test
  void shouldReturnAccountBalanceIfAccountExisted() {
    assertFalse(true);
  }

  @AfterAll
  static void stopServer() {
    if (server != null) {
      server.stop();
    }
  }
}