package com.revolut.transfers.model;

import java.math.BigDecimal;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class NewTransferDto {

  /**
   * Identifier of sender account
   */
  @NotEmpty
  private String from;

  /**
   * Identifier of receiver account
   */
  @NotEmpty
  private String to;

  /**
   * Amount of money for transfer
   */
  @NotNull
  @Positive
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
