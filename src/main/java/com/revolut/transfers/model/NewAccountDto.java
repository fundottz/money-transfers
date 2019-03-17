package com.revolut.transfers.model;

import java.math.BigDecimal;
import javax.validation.constraints.Min;

public class NewAccountDto {

  /**
   * Initial account balance
   */
  @Min(value = 0, message = "The amount must be positive")
  private BigDecimal balance;

  public BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }
}
