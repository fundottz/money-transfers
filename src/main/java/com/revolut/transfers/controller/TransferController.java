package com.revolut.transfers.controller;

import com.revolut.transfers.exception.TransferIsNotPossibleException;
import com.revolut.transfers.model.NewTransferDto;
import com.revolut.transfers.model.TransferDto;
import com.revolut.transfers.service.TransferProcessor;
import com.revolut.transfers.service.TransferService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.validation.Validated;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import javax.validation.Valid;

@Validated
@Controller("/api/transfers")
public class TransferController {

  @Inject
  private TransferService transferService;

  @Inject
  private TransferProcessor transferProcessor;

  @Post
  public TransferDto create(@Valid NewTransferDto transfer) {
    var processable = transferProcessor.isProcessable(transfer);

    if (processable) {
      return transferService.create(transfer);
    } else {
      throw new TransferIsNotPossibleException();
    }
  }

  @Get("/{id}")
  public TransferDto getById(final String id) {
    throw new RuntimeException("");
  }

  @Get("/{?from,to}")
  public List<TransferDto> find(Optional<String> from, Optional<String> to) {
    var transferDto = new TransferDto();
    transferDto.setFrom(from.orElse(null));
    transferDto.setTo(to.orElse(null));

    return Collections.singletonList(transferDto);
  }

}
