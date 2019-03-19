package com.revolut.transfers.exception;

public class TransferNotPossibleException extends RuntimeException {

  public TransferNotPossibleException() {
    super("Transfer is not possible");
  }
}
