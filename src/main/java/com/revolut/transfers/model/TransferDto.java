package com.revolut.transfers.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransferDto {

  /**
   * Identifier of sender account
   */
  private String from;

  /**
   * Identifier of receiver account
   */
  private String to;

  /**
   * Amount of money for transfer
   */
  private BigDecimal amount;

  /**
   * Datetime when transfer created
   */
  private LocalDateTime created;

  /**
   * Datetime when transfer processed due to processing queue
   */
  private LocalDateTime processed;

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

  public LocalDateTime getCreated() {
    return created;
  }

  public void setCreated(LocalDateTime created) {
    this.created = created;
  }

  public LocalDateTime getProcessed() {
    return processed;
  }

  public void setProcessed(LocalDateTime processed) {
    this.processed = processed;
  }
}
