package com.revolut.transfers.exception.handler;

import com.revolut.transfers.exception.NotEnoughMoneyException;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import javax.inject.Singleton;

@Produces
@Singleton
@Requires(classes = {NotEnoughMoneyException.class, ExceptionHandler.class})
public class NotEnoughMoneyExceptionHandler implements
    ExceptionHandler<NotEnoughMoneyException, HttpResponse> {

  @Override
  public HttpResponse handle(HttpRequest request, NotEnoughMoneyException exception) {
    return HttpResponse.status(HttpStatus.CONFLICT);
  }
}
