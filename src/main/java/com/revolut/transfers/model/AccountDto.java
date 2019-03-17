package com.revolut.transfers.model;

import java.math.BigDecimal;

public class AccountDto {

  private String id;

  private BigDecimal balance;

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

}
