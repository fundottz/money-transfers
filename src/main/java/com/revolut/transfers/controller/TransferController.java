package com.revolut.transfers.controller;

import com.revolut.transfers.model.NewTransferDto;
import com.revolut.transfers.model.TransferDto;
import com.revolut.transfers.model.TransferStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller("/api/transfers")
public class TransferController {

  @Post
  public TransferDto create(NewTransferDto transfer) {
    throw new RuntimeException("");
  }

  @Get("/{id}")
  public TransferDto getById(final String id) {
    throw new RuntimeException("");
  }

  @Get("/{?from,to}")
  public List<TransferDto> find(Optional<String> from, Optional<String> to) {
    TransferDto transferDto = new TransferDto();
    transferDto.setFrom(from.orElse(null));
    transferDto.setTo(to.orElse(null));

    boolean empty = from.isEmpty();

    return Collections.singletonList(transferDto);
  }

  @Get("/{id}/status")
  public TransferStatus getStatus(final String id) {
    throw new RuntimeException("");
  }
}
