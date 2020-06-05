package com.fundottz.transfers.controller;

import static io.micronaut.http.HttpRequest.GET;
import static io.micronaut.http.HttpRequest.POST;
import static io.micronaut.http.HttpStatus.CREATED;
import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fundottz.transfers.model.Account;
import com.fundottz.transfers.model.CreateAccountCommand;
import com.fundottz.transfers.model.CreateTransferCommand;
import com.fundottz.transfers.model.Transfer;
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

class TransferControllerTest {

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
  void shouldNotCreateTransferIfFromEqualsTo() {
    var id = "123";
    var transfer = new CreateTransferCommand();
    transfer.setFrom(id);
    transfer.setTo(id);
    transfer.setAmount(BigDecimal.TEN);

    var request = POST("/api/transfers", transfer);
    var clientException = assertThrows(
        HttpClientResponseException.class,
        () -> client.exchange(request, Transfer.class));

    assertEquals(HttpStatus.BAD_REQUEST, clientException.getResponse().status());
  }

  @Test
  void shouldNotCreateTransferIfAmountIsNegative() {
    var transfer = new CreateTransferCommand();
    transfer.setFrom("1");
    transfer.setTo("2");
    transfer.setAmount(BigDecimal.TEN.negate());

    var request = POST("/api/transfers", transfer);
    var clientException = assertThrows(
        HttpClientResponseException.class,
        () -> client.exchange(request, Transfer.class));

    assertEquals(HttpStatus.BAD_REQUEST, clientException.getResponse().status());
  }

  @Test
  void shouldNotCreateTransferIfFromAndToNotSet() {
    var transfer = new CreateTransferCommand();
    transfer.setAmount(BigDecimal.TEN);

    var request = POST("/api/transfers", transfer);
    var clientException = assertThrows(
        HttpClientResponseException.class,
        () -> client.exchange(request, Transfer.class));

    assertEquals(HttpStatus.BAD_REQUEST, clientException.getResponse().status());
  }

  @Test
  void shouldNotCreateTransferIfMoneyNotEnoughAndReturnConflict(){
    Account account1 = createAccount(ZERO);
    Account account2 = createAccount(TEN);

    var transfer = new CreateTransferCommand();
    transfer.setFrom(account1.getId());
    transfer.setTo(account2.getId());
    transfer.setAmount(valueOf(100));
    var request = POST("/api/transfers", transfer);

    var clientException = assertThrows(
        HttpClientResponseException.class,
        () -> client.exchange(request, Transfer.class));
    assertEquals(HttpStatus.CONFLICT, clientException.getResponse().status());
  }

  @Test
  void shouldCreateValidTransferIfAccountsExistsAndReturnCreated() {
    Account account1 = createAccount(TEN);
    Account account2 = createAccount(ZERO);

    var transfer = new CreateTransferCommand();
    transfer.setFrom(account1.getId());
    transfer.setTo(account2.getId());
    transfer.setAmount(TEN);

    var request = POST("/api/transfers", transfer);
    var createdTransfer = client.exchange(request, Transfer.class);

    assertEquals(CREATED, createdTransfer.status());
    assertNotNull(createdTransfer.body());
  }

  @Test
  void shouldReturnNotFoundIfTransferNotExisted() {
    var request = GET("/api/transfers/0");

    var clientException = assertThrows(
        HttpClientResponseException.class,
        () -> client.exchange(request, Transfer.class));
    assertEquals(HttpStatus.NOT_FOUND, clientException.getResponse().status());
  }

  private Account createAccount(BigDecimal balance){
    var accountCommand = new CreateAccountCommand(balance);

    var request = POST("/api/accounts", accountCommand);
    var createdAccount = client.exchange(request, Account.class);

    return createdAccount.body();
  }

  @AfterAll
  static void stopServer() {
    if (server != null) {
      server.stop();
    }
  }
}
