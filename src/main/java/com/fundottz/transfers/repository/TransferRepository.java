package com.fundottz.transfers.repository;

import com.fundottz.transfers.model.CreateTransferCommand;
import com.fundottz.transfers.model.Transfer;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;

public interface TransferRepository {

  Transfer create(CreateTransferCommand transfer);

  Optional<Transfer> getById(String id);

  List<Transfer> find(@Nullable String from, @Nullable String to);

  String nextId();
}
