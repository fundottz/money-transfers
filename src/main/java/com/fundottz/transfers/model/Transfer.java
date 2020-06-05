package com.fundottz.transfers.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transfer {

  /**
   * Identifier of executed transfer
   */
  private String id;

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

  public Transfer(String id, String from, String to, BigDecimal amount) {
    this.id = id;
    this.from = from;
    this.to = to;
    this.amount = amount;
    this.created = LocalDateTime.now();
  }

  public Transfer() {
  }

  public String getFrom() {
    return from;
  }

  public String getTo() {
    return to;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public LocalDateTime getCreated() {
    return created;
  }
}
