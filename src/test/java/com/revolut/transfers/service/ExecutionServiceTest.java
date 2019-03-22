package com.revolut.transfers.service;

import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;
import static java.util.stream.IntStream.range;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.revolut.transfers.model.CreateAccountCommand;
import com.revolut.transfers.model.CreateTransferCommand;
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

  private AccountRepository repository = new AccountInMemoryRepository();
  private ExecutionService executionService = new ExecutionService(repository);

  @Test
  void shouldProperHandleConcurrentTransfersBetweenTwoAccounts() throws InterruptedException {
    var firstAccount = repository.create(new CreateAccountCommand(valueOf(1000)));
    var secondAccount = repository.create(new CreateAccountCommand(ZERO));

    var executor = Executors.newFixedThreadPool(10);

    Callable<Boolean> callResult = () -> {
      var transfer = new CreateTransferCommand();
      transfer.setFrom(firstAccount.getId());
      transfer.setTo(secondAccount.getId());
      transfer.setAmount(valueOf(10));
      executionService.execute(transfer);
      return true;
    };

    var tasks = range(0, 100).mapToObj(i -> callResult)
        .collect(Collectors.toList());

    executor.invokeAll(tasks);

    assertEquals(ZERO, firstAccount.getBalance());
    assertEquals(valueOf(1000), secondAccount.getBalance());
  }

  @Test
  void shouldHandle2of3ConcurrentTransfersWhenExecutedOnSameAccountAndOneIsOutOfMoney()
      throws InterruptedException {
    var firstAccount = repository.create(new CreateAccountCommand(ZERO));
    var secondAccount = repository.create(new CreateAccountCommand(valueOf(100)));
    var thirdAccount = repository.create(new CreateAccountCommand(valueOf(50)));

    List<Callable<Boolean>> tasks = Arrays.asList(
        () -> {
          var transfer = new CreateTransferCommand();
          transfer.setFrom(secondAccount.getId());
          transfer.setTo(firstAccount.getId());
          transfer.setAmount(valueOf(100));
          executionService.execute(transfer);
          return true;
        },
        () -> {
          var transfer = new CreateTransferCommand();
          transfer.setFrom(thirdAccount.getId());
          transfer.setTo(firstAccount.getId());
          transfer.setAmount(valueOf(25));
          executionService.execute(transfer);
          return true;
        },
        () -> {
          var transfer = new CreateTransferCommand();
          transfer.setFrom(secondAccount.getId());
          transfer.setTo(firstAccount.getId());
          transfer.setAmount(valueOf(50));
          executionService.execute(transfer);
          return true;
        }
    );

    var count = new AtomicInteger();
    var executor = Executors.newFixedThreadPool(3);
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