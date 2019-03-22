package com.revolut.transfers.model;

import java.math.BigDecimal;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class CreateTransferCommand {

  /**
   * Identifier of sender account
   */
  @NotEmpty(message = "Transfer sender must be specified")
  private String from;

  /**
   * Identifier of recipient account
   */
  @NotEmpty(message = "Transfer recipient must be specified")
  private String to;

  /**
   * Amount of money for transfer
   */
  @NotNull
  @Positive(message = "The amount must be positive or zero")
  private BigDecimal amount;

  public String getFrom() {
    return from;
  }

  public void setFrom(String from) {
    this.from = from;
  }

  public String getTo() {
    return to;
  }

  public void setTo(String to) {
    this.to = to;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }
}
