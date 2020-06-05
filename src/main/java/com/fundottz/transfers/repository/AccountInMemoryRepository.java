package com.fundottz.transfers.repository;

import com.fundottz.transfers.model.Account;
import com.fundottz.transfers.model.CreateAccountCommand;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import javax.inject.Singleton;
import javax.validation.constraints.NotNull;

@Singleton
public class AccountInMemoryRepository implements AccountRepository {

  private final ConcurrentHashMap<String, Account> accounts = new ConcurrentHashMap<>();
  private final AtomicInteger counter = new AtomicInteger();

  @Override
  public Account create(final CreateAccountCommand newAccount) {
    var id = nextId();
    var account = new Account(id, newAccount.getBalance());
    accounts.put(id, account);
    return account;
  }

  @Override
  public List<Account> getAll() {
    return new ArrayList<>(accounts.values());
  }

  @Override
  public synchronized Optional<Account> getById(@NotNull String id) {
    return Optional.ofNullable(accounts.get(id));
  }

  @Override
  public Optional<BigDecimal> getAccountBalance(String id) {
    return getById(id).map(Account::getBalance);
  }

  @Override
  public String nextId() {
    return String.valueOf(counter.incrementAndGet());
  }
}
