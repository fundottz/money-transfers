package com.revolut.transfers.repository;

import com.revolut.transfers.model.Account;
import com.revolut.transfers.model.NewAccountDto;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import javax.inject.Singleton;
import javax.validation.constraints.NotNull;

@Singleton
public class AccountInMemoryRepository implements AccountRepository {

  private ConcurrentHashMap<String, Account> accounts = new ConcurrentHashMap<>();
  private AtomicInteger counter = new AtomicInteger();

  @Override
  public Account create(final NewAccountDto newAccountDto) {
    var id = nextId();
    var account = new Account();
    account.setId(id);
    account.setBalance(newAccountDto.getBalance());
    account.setCreated(LocalDateTime.now());
    accounts.put(id, account);
    return account;
  }

  @Override
  public List<Account> getAll() {
    return new ArrayList<>(accounts.values());
  }

  @Override
  public Optional<Account> getById(@NotNull String id) {
    return Optional.ofNullable(accounts.get(id));
  }

  @Override
  public Optional<BigDecimal> getAccountBalance(String id) {
    return getById(id)
        .map(Account::getBalance);
  }

  @Override
  public boolean checkExists(String id) {
    return getById(id).isPresent();
  }

  @Override
  public String nextId() {
    return String.valueOf(counter.incrementAndGet());
  }
}
