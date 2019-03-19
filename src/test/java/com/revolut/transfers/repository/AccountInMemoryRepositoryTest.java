package com.revolut.transfers.repository;

import static java.math.BigDecimal.TEN;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.revolut.transfers.model.Account;
import com.revolut.transfers.model.NewAccountCommand;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class AccountInMemoryRepositoryTest {

  AccountRepository repository = new AccountInMemoryRepository();
  Logger logger = LoggerFactory.getLogger(AccountInMemoryRepositoryTest.class);

  @Test
  void create() {

    var account = new NewAccountCommand();
    account.setBalance(BigDecimal.ZERO);
    Account acc = repository.create(account);
    String id = acc.getId();


    var executor = Executors.newFixedThreadPool(3);

    Thread t1 = new Thread(() -> {
      Optional<BigDecimal> balance = repository.getAccountBalance(id);
      logger.info("Balance before {}", balance.orElse(null));
    });

    Thread t2 = new Thread(() -> {
      logger.info("Withdrawed to ");
      repository.getById(id).ifPresent(account1 -> {
        account1.withdraw(TEN);
      });
    });

    Thread t3 = new Thread(() -> {
      logger.info("Getting balance after");

      BigDecimal balance = repository.getAccountBalance(id).orElse(null);
      logger.info("Balance is {}", balance);
      assertEquals(TEN, balance);
    });

    executor.submit(t1);
    executor.submit(t2);
    executor.submit(t3);
  }

  @Test
  void getAccountBalance() {
  }

  @Test
  void checkExists() {
  }
}