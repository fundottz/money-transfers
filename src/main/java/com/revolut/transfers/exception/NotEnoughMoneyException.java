package com.revolut.transfers.exception;

public class NotEnoughMoneyException extends RuntimeException {

  public NotEnoughMoneyException() {
    super("Not enough money for transfer");
  }
}
