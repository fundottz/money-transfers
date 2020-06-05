package com.fundottz.transfers.exception.handler;

import com.fundottz.transfers.exception.TransferNotFoundException;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import javax.inject.Singleton;

@Produces
@Singleton
@Requires(classes = {TransferNotFoundException.class, ExceptionHandler.class})
public class TransferNotFoundExceptionHandler implements
    ExceptionHandler<TransferNotFoundException, HttpResponse> {

  @Override
  public HttpResponse handle(HttpRequest request, TransferNotFoundException exception) {
    return HttpResponse.notFound();
  }
}
