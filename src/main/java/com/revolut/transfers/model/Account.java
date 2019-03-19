package com.revolut.transfers.model;

import com.revolut.transfers.exception.NotEnoughMoneyException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Account {

  private String id;

  private BigDecimal balance;

  private LocalDateTime created;

  public Account(String id, BigDecimal balance) {
    this.id = id;
    this.balance = balance;
    this.created = LocalDateTime.now();
  }


  public String getId() {
    return id;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public LocalDateTime getCreated() {
    return created;
  }

  public void deposit(BigDecimal amount) {
    this.balance = this.balance.add(amount);
  }

  public void withdraw(final BigDecimal amount) throws NotEnoughMoneyException {
    if (this.balance.compareTo(amount) >= 0) {
      this.balance = this.balance.subtract(amount);
    } else {
      throw new NotEnoughMoneyException();
    }
  }
}
