package com.fundottz.transfers.repository;

import com.fundottz.transfers.model.Account;
import com.fundottz.transfers.model.CreateAccountCommand;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AccountRepository {

  Account create(CreateAccountCommand createAccountCommand);

  List<Account> getAll();

  Optional<Account> getById(String id);

  Optional<BigDecimal> getAccountBalance(String id);

  String nextId();
}
