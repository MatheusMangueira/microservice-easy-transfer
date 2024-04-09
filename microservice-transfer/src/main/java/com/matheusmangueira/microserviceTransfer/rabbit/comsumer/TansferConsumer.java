package com.matheusmangueira.microserviceTransfer.rabbit.comsumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dtos.TransferRequestDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TansferConsumer {

  @RabbitListener(queues = "transfer-row")
  public void consumer(String message) throws JsonProcessingException, InterruptedException {
    TransferRequestDTO transferRequestDTO = new ObjectMapper().readValue(message, TransferRequestDTO.class);

    BigDecimal subtract = transferRequestDTO.senderID.balance()
        .subtract(transferRequestDTO.value);

    BigDecimal add = transferRequestDTO.recipientID.balance()
        .add(transferRequestDTO.value);

    System.out.println("Transfer: " + message);
    System.out.println("Sender: " + transferRequestDTO.senderID.balance());
    System.out.println("subtract Sender: " + subtract);
    System.out.println("Recipient: " + transferRequestDTO.recipientID.balance());
    System.out.println("add Recipient: " + add);
    System.out.println("Value: " + transferRequestDTO.senderID.name());
    System.out.println("Value: " + transferRequestDTO.recipientID.name());
    System.out.println("-------------------------");
  }
}
