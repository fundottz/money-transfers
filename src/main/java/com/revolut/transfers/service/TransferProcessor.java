package com.revolut.transfers.service;

import com.revolut.transfers.model.NewTransferDto;
import com.revolut.transfers.repository.AccountRepository;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TransferProcessor {

  @Inject
  private AccountRepository accountRepository;

  public boolean isProcessable(NewTransferDto transfer) {
    var from = transfer.getFrom();
    var to = transfer.getTo();

    if (from.equals(to)) {
      return false;
    }

    var fromExists = accountRepository.checkExists(from);
    var toExists = accountRepository.checkExists(to);

    return fromExists && toExists;
  }

  public void process(NewTransferDto transfer) {

    var amount = transfer.getAmount();

    accountRepository.getById(transfer.getFrom())
        .ifPresent(account -> account.withdrawal(amount));
    accountRepository.getById(transfer.getTo())
        .ifPresent(account -> account.deposit(amount));

  }
}
