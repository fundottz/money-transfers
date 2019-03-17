package com.revolut.transfers.model;

import java.math.BigDecimal;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
  @Min(value = 0, message = "The amount must be positive")
  private BigDecimal amount;
}
