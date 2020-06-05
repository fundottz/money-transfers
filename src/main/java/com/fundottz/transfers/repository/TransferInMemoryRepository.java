package com.fundottz.transfers.repository;

import static java.util.stream.Collectors.toList;

import com.fundottz.transfers.model.CreateTransferCommand;
import com.fundottz.transfers.model.Transfer;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nullable;
import javax.inject.Singleton;

@Singleton
public class TransferInMemoryRepository implements TransferRepository {

  private final ConcurrentHashMap<String, Transfer> transfers = new ConcurrentHashMap<>();
  private final AtomicInteger counter = new AtomicInteger();

  @Override
  public Transfer create(CreateTransferCommand newTransfer) {
    var id = nextId();
    var from = newTransfer.getFrom();
    var to = newTransfer.getTo();
    var amount = newTransfer.getAmount();

    var transfer = new Transfer(id, from, to, amount);
    this.transfers.put(id, transfer);

    return transfer;
  }

  @Override
  public Optional<Transfer> getById(String id) {
    return Optional.ofNullable(this.transfers.get(id));
  }

  @Override
  public List<Transfer> find(@Nullable String from, @Nullable String to) {
    return this.transfers.values().stream()
        .filter(t -> t.getFrom().equals(from) || isEmptyOrNull(from))
        .filter(t -> t.getTo().equals(to) || isEmptyOrNull(to))
        .collect(toList());
  }

  private boolean isEmptyOrNull(String value) {
    return value == null || value.isBlank();
  }

  @Override
  public String nextId() {
    return String.valueOf(counter.incrementAndGet());
  }
}
