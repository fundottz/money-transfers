package com.revolut.transfers.controller;

import static io.micronaut.http.HttpStatus.CREATED;

import com.revolut.transfers.model.CreateTransferCommand;
import com.revolut.transfers.model.Transfer;
import com.revolut.transfers.service.TransferService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Status;
import io.micronaut.validation.Validated;
import java.util.List;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.validation.Valid;

@Validated
@Controller("/api/transfers")
public class TransferController {

  private final TransferService transferService;

  @Inject
  public TransferController(TransferService transferService) {
    this.transferService = transferService;
  }

  @Post
  @Status(CREATED)
  public Transfer create(@Valid CreateTransferCommand transfer) {
    return transferService.create(transfer);
  }

  @Get("/{id}")
  public Transfer getById(final String id) {
    return transferService.getById(id);
  }

  @Get("/{?from,to}")
  public List<Transfer> find(@Nullable String from, @Nullable String to) {
    return transferService.find(from, to);
  }
}
