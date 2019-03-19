package com.revolut.transfers.service;

import com.revolut.transfers.exception.TransferNotFoundException;
import com.revolut.transfers.model.NewTransferCommand;
import com.revolut.transfers.model.Transfer;
import com.revolut.transfers.repository.TransferRepository;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TransferService {

  @Inject
  private TransferRepository transferRepository;

  @Inject
  private ExecutionService executionService;

  public Transfer create(NewTransferCommand transfer) {
    executionService.execute(transfer);
    return transferRepository.create(transfer);
  }

  public Transfer getById(String id) {
    return transferRepository.getById(id)
        .orElseThrow(TransferNotFoundException::new);
  }

  public List<Transfer> find(String from, String to) {
    return transferRepository.find(from, to);
  }
}
