package com.revolut.transfers.service;

import com.revolut.transfers.exception.AccountNotFoundException;
import com.revolut.transfers.exception.NotEnoughMoneyException;
import com.revolut.transfers.exception.TransferNotPossibleException;
import com.revolut.transfers.model.Account;
import com.revolut.transfers.model.CreateTransferCommand;
import com.revolut.transfers.repository.AccountRepository;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class ExecutionService {

  private final AccountRepository accountRepository;
  private Logger logger = LoggerFactory.getLogger(ExecutionService.class);

  @Inject
  public ExecutionService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  public void execute(CreateTransferCommand transfer) throws NotEnoughMoneyException {
    String from = transfer.getFrom();
    String to = transfer.getTo();

    if (from.equals(to)) {
      throw new TransferNotPossibleException();
    }

    var amount = transfer.getAmount();
    var transferFrom = accountRepository.getById(from);
    var transferTo = accountRepository.getById(to);

    if (transferFrom.isPresent() && transferTo.isPresent()) {
      Account accountFrom = transferFrom.get();
      accountFrom.withdraw(amount);
      logger.info("Withdraw account {} by {}", accountFrom.getId(), amount);

      Account accountTo = transferTo.get();
      accountTo.deposit(amount);
      logger.info("Deposit account {} by {}", accountTo.getId(), amount);
    } else {
      throw new AccountNotFoundException();
    }
  }
}
