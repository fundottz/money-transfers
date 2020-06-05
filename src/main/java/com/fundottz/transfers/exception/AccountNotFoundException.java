package com.fundottz.transfers.exception;

public class AccountNotFoundException extends RuntimeException {

  public AccountNotFoundException() {
    super("Account not found");
  }
}
