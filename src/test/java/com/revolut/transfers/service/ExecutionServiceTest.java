package com.revolut.transfers.service;

import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;

import com.revolut.transfers.model.NewAccountDto;
import com.revolut.transfers.model.NewTransferDto;
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
    NewAccountDto account1 = new NewAccountDto();
    account1.setBalance(ZERO);
    accountRepository.create(account1);

    NewAccountDto account2 = new NewAccountDto();
    account1.setBalance(TEN);
    accountRepository.create(account1);
  }

  @Test
  void process() {
    NewTransferDto transfer = new NewTransferDto();
    transfer.setAmount(valueOf(100));
    transfer.setFrom("2");
    transfer.setTo("1");
    executionService.execute(transfer);
  }
}