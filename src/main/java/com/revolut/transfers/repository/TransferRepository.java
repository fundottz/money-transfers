package com.revolut.transfers.repository;

import com.revolut.transfers.model.NewTransferCommand;
import com.revolut.transfers.model.Transfer;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;

public interface TransferRepository {

  Transfer create(NewTransferCommand transfer);

  Optional<Transfer> getById(String id);

  List<Transfer> find(@Nullable String from, @Nullable String to);

  String nextId();
}
