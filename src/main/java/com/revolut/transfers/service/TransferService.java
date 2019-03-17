package com.revolut.transfers.service;

import com.revolut.transfers.model.NewTransferDto;
import com.revolut.transfers.model.TransferDto;
import com.revolut.transfers.repository.TransferRepository;
import javax.inject.Inject;

public class TransferService {

  @Inject
  private TransferRepository transferRepository;

  public TransferDto create(NewTransferDto transfer) {
    return transferRepository.create(transfer);
  }
}
