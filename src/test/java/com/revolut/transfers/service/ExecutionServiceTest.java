package com.revolut.transfers.service;

import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;
import static java.util.stream.IntStream.range;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.revolut.transfers.model.Account;
import com.revolut.transfers.model.NewAccountCommand;
import com.revolut.transfers.model.NewTransferCommand;
import com.revolut.transfers.repository.AccountInMemoryRepository;
import com.revolut.transfers.repository.AccountRepository;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class ExecutionServiceTest {

  AccountRepository repository = new AccountInMemoryRepository();
  ExecutionService executionService = new ExecutionService(repository);

  @Test
  void shouldProperHandleConcurrentTransfersBetweenTwoAccounts() throws InterruptedException {
    Account firstAccount = repository.create(new NewAccountCommand(valueOf(1000)));
    Account secondAccount = repository.create(new NewAccountCommand(ZERO));

    var executor = Executors.newFixedThreadPool(10);

    Callable<Boolean> booleanCallable = () -> {
      var transfer = new NewTransferCommand();
      transfer.setFrom(firstAccount.getId());
      transfer.setTo(secondAccount.getId());
      transfer.setAmount(valueOf(10));
      executionService.execute(transfer);
      return true;
    };

    List<Callable<Boolean>> tasks = range(0, 100).mapToObj(i -> booleanCallable)
        .collect(Collectors.toList());

    executor.invokeAll(tasks);

    assertEquals(ZERO, firstAccount.getBalance());
    assertEquals(valueOf(1000), secondAccount.getBalance());
  }

  @Test
  void shouldHandle2of3ConcurrentTransfersWhenExecutedOnSameAccountAndOneIsOutOfMoney()
      throws InterruptedException {
    Account firstAccount = repository.create(new NewAccountCommand(ZERO));
    Account secondAccount = repository.create(new NewAccountCommand(valueOf(100)));
    Account thirdAccount = repository.create(new NewAccountCommand(valueOf(50)));

    List<Callable<Boolean>> tasks = Arrays.asList(
        () -> {
          var transfer = new NewTransferCommand();
          transfer.setFrom(secondAccount.getId());
          transfer.setTo(firstAccount.getId());
          transfer.setAmount(valueOf(100));
          executionService.execute(transfer);
          return true;
        },
        () -> {
          var transfer = new NewTransferCommand();
          transfer.setFrom(thirdAccount.getId());
          transfer.setTo(firstAccount.getId());
          transfer.setAmount(valueOf(25));
          executionService.execute(transfer);
          return true;
        },
        () -> {
          var transfer = new NewTransferCommand();
          transfer.setFrom(secondAccount.getId());
          transfer.setTo(firstAccount.getId());
          transfer.setAmount(valueOf(50));
          executionService.execute(transfer);
          return true;
        }
    );

    var executor = Executors.newFixedThreadPool(3);
    AtomicInteger count = new AtomicInteger();
    executor.invokeAll(tasks).forEach(f -> {
      try {
        if (f.get()) {
          count.getAndIncrement();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    });

    assertEquals(count.get(), 2);
  }
}