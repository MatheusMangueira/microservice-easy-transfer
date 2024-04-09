package com.matheusmangueira.microserviceTransfer.exceptions;

import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.util.ErrorHandler;

public class ErrorHandlerRabbitmq implements ErrorHandler {

  @Override
  public void handleError(Throwable t) {
    String nameRow = ((ListenerExecutionFailedException) t)
        .getFailedMessage()
        .getMessageProperties()
        .getConsumerQueue();

    String message = new String(((ListenerExecutionFailedException) t)
        .getFailedMessage()
        .getBody());

    System.out.println(t.getCause().getMessage());

    throw new TransferFailedException("Error in the queue: " + nameRow + " Message: " + message, t);
  }
}
