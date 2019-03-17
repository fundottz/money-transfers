package com.revolut.transfers.repository;

import com.revolut.transfers.model.NewTransferDto;
import com.revolut.transfers.model.TransferDto;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import javax.inject.Singleton;

@Singleton
public class TransferInMemoryRepository implements TransferRepository {

  private ConcurrentHashMap<String, TransferDto> transfers = new ConcurrentHashMap<>();
  private AtomicInteger counter = new AtomicInteger();

  @Override
  public TransferDto create(NewTransferDto transfer) {
    return null;
  }

  @Override
  public boolean checkExists(String id) {
    return getById(id).isPresent();
  }

  @Override
  public Optional<TransferDto> getById(String id) {
    return Optional.ofNullable(transfers.get(id));
  }

  @Override
  public String nextId() {
    return String.valueOf(counter.incrementAndGet());
  }
}
