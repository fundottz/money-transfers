package com.revolut.transfers.exception;

public class TransferIsNotPossibleException extends RuntimeException {

  public TransferIsNotPossibleException() {
    super("Transfer is not possible");
  }
}
