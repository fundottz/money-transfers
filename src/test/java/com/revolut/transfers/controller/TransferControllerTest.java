package com.revolut.transfers.controller;

import static io.micronaut.http.HttpRequest.POST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.revolut.transfers.model.NewTransferCommand;
import com.revolut.transfers.model.Transfer;
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
  void shouldCreateValidTransferIfAccountsExistsAndReturnCreated() {

    assertFalse(true);
  }

  @Test
  void shouldNotCreateTransferIfFromEqualsTo() {
    var id = "123";
    var transfer = new NewTransferCommand();
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
    var transfer = new NewTransferCommand();
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
    var transfer = new NewTransferCommand();
    transfer.setAmount(BigDecimal.TEN);

    var request = POST("/api/transfers", transfer);

    var clientException = assertThrows(
        HttpClientResponseException.class,
        () -> client.exchange(request, Transfer.class));
    assertEquals(HttpStatus.BAD_REQUEST, clientException.getResponse().status());
  }

  @AfterAll
  static void stopServer() {
    if (server != null) {
      server.stop();
    }
  }
}
