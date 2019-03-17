package com.revolut.transfers.repository;

import com.revolut.transfers.model.NewTransferDto;
import com.revolut.transfers.model.TransferDto;
import java.util.Optional;

public interface TransferRepository extends Repository {

  TransferDto create(NewTransferDto transfer);

  Optional<TransferDto> getById(String id);

}
