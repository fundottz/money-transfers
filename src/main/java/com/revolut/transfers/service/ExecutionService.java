package com.revolut.transfers.service;

import com.revolut.transfers.exception.AccountNotFoundException;
import com.revolut.transfers.exception.TransferNotPossibleException;
import com.revolut.transfers.model.NewTransferCommand;
import com.revolut.transfers.repository.AccountRepository;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ExecutionService {

  @Inject
  private AccountRepository accountRepository;

  public boolean isExecutable(NewTransferCommand transfer) {
    var from = transfer.getFrom();
    var to = transfer.getTo();

    return !from.equals(to);
  }

  public void execute(NewTransferCommand transfer) {
    String from = transfer.getFrom();
    String to = transfer.getTo();

    if (from.equals(to)) {
      throw new TransferNotPossibleException();
    }

    var amount = transfer.getAmount();
    var transferFrom = accountRepository.getById(from);
    var transferTo = accountRepository.getById(to);

    if (transferFrom.isPresent() && transferTo.isPresent()) {
      transferFrom.get().withdraw(amount);
      transferTo.get().deposit(amount);
    } else {
      throw new AccountNotFoundException();
    }
  }
}
