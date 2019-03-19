package com.revolut.transfers.service;

import com.revolut.transfers.exception.AccountNotFoundException;
import com.revolut.transfers.model.NewTransferDto;
import com.revolut.transfers.repository.AccountRepository;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ExecutionService {

  @Inject
  private AccountRepository accountRepository;

  public boolean isExecutable(NewTransferDto transfer) {
    var from = transfer.getFrom();
    var to = transfer.getTo();

    if (from.equals(to)) {
      return false;
    }

    var fromExists = accountRepository.checkExists(from);
    var toExists = accountRepository.checkExists(to);

    return fromExists && toExists;
  }

  public void execute(NewTransferDto transfer) {
    var amount = transfer.getAmount();

    var fromAccount = accountRepository.getById(transfer.getFrom())
        .orElseThrow(AccountNotFoundException::new);
    fromAccount.withdraw(amount);

    var toAccount = accountRepository.getById(transfer.getTo())
        .orElseThrow(AccountNotFoundException::new);
    toAccount.deposit(amount);
  }
}
