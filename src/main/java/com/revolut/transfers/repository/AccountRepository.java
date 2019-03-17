package com.revolut.transfers.repository;

import com.revolut.transfers.model.Account;
import com.revolut.transfers.model.NewAccountDto;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AccountRepository extends Repository {

  Account create(NewAccountDto newAccountDto);

  List<Account> getAll();

  Optional<Account> getById(String id);

  Optional<BigDecimal> getAccountBalance(String id);
}
