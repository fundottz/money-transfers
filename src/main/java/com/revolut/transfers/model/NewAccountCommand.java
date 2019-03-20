package com.revolut.transfers.model;

import java.math.BigDecimal;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class NewAccountCommand {

  /**
   * Initial account balance
   */
  @NotNull
  @Min(value = 0, message = "The amount must be positive or zero")
  private BigDecimal balance;

  public BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }

  public NewAccountCommand(BigDecimal balance) {
    this.balance = balance;
  }

  public NewAccountCommand() {
  }
}
