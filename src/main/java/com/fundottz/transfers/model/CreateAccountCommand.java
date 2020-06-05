package com.fundottz.transfers.model;

import java.math.BigDecimal;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CreateAccountCommand {

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

  public CreateAccountCommand(BigDecimal balance) {
    this.balance = balance;
  }

  public CreateAccountCommand() {
  }
}
