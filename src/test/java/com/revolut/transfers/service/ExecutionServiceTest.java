package com.revolut.transfers.service;

import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;

import com.revolut.transfers.model.NewAccountCommand;
import com.revolut.transfers.model.NewTransferCommand;
import com.revolut.transfers.repository.AccountRepository;
import io.micronaut.test.annotation.MicronautTest;
import javax.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@MicronautTest
class ExecutionServiceTest {

  @Inject
  ExecutionService executionService;

  @Inject
  AccountRepository accountRepository;

  @Test
  void isProcessable() {
  }

  @BeforeEach
  private void testData(){
    NewAccountCommand account1 = new NewAccountCommand();
    account1.setBalance(ZERO);
    accountRepository.create(account1);

    NewAccountCommand account2 = new NewAccountCommand();
    account1.setBalance(TEN);
    accountRepository.create(account1);
  }

  @Test
  void process() {
    NewTransferCommand transfer = new NewTransferCommand();
    transfer.setAmount(valueOf(100));
    transfer.setFrom("2");
    transfer.setTo("1");
    executionService.execute(transfer);
  }
}