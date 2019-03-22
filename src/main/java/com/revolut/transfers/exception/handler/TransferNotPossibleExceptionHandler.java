package com.revolut.transfers.exception.handler;

import com.revolut.transfers.exception.AccountNotFoundException;
import com.revolut.transfers.exception.TransferNotPossibleException;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import javax.inject.Singleton;

@Produces
@Singleton
@Requires(classes = {AccountNotFoundException.class, ExceptionHandler.class})
public class TransferNotPossibleExceptionHandler implements
    ExceptionHandler<TransferNotPossibleException, HttpResponse> {

  @Override
  public HttpResponse handle(HttpRequest request, TransferNotPossibleException exception) {
    return HttpResponse.badRequest();
  }
}
