package com.fundottz.transfers.service;

import com.fundottz.transfers.exception.TransferNotFoundException;
import com.fundottz.transfers.model.CreateTransferCommand;
import com.fundottz.transfers.model.Transfer;
import com.fundottz.transfers.repository.TransferRepository;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TransferService {

  private final TransferRepository transferRepository;
  private final ExecutionService executionService;

  @Inject
  public TransferService(TransferRepository transferRepository,
      ExecutionService executionService) {
    this.transferRepository = transferRepository;
    this.executionService = executionService;
  }

  public Transfer create(CreateTransferCommand transfer) {
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
