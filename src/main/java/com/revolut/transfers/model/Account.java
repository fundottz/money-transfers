package com.revolut.transfers.model;

import com.revolut.transfers.exception.NotEnoughMoneyException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Account {

  private String id;

  private BigDecimal balance;

  private LocalDateTime created;

  public void deposit(BigDecimal amount) {
    this.balance = this.balance.add(amount);
  }

  public void withdraw(final BigDecimal amount) throws NotEnoughMoneyException {
    if (this.balance.compareTo(amount) != -1) {
      this.balance = this.balance.subtract(amount);
    } else {
      throw new NotEnoughMoneyException();
    }
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }

  public LocalDateTime getCreated() {
    return created;
  }

  public void setCreated(LocalDateTime created) {
    this.created = created;
  }

}
