package com.matheusmangueira.microserviceTransfer.rabbit.comsumer;

import dtos.TransferRequestDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TansferConsumer {

  @RabbitListener(queues = "transfer-row")
  public void consumer(String message) {
    System.out.println("Transfer: " + message);
    System.out.println("-------------------------");
  }
}
