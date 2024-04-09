package com.matheusmangueira.microserviceTransfer.rabbit.comsumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.matheusmangueira.microserviceTransfer.services.TransferService;
import dtos.TransferRequestDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.math.BigDecimal;

@Component
public class TansferConsumer {
  @Autowired
  private TransferService transferService;

  @RabbitListener(queues = "transfer-row")
  public void consumer(String message) throws JsonProcessingException, InterruptedException {
    TransferRequestDTO transferRequestDTO = new ObjectMapper().readValue(message, TransferRequestDTO.class);

    transferService.transferAccount(
        transferRequestDTO.value,
        transferRequestDTO.senderID,
        transferRequestDTO.recipientID
    );


    System.out.println("Transfer: " + message);
    System.out.println("Sender: " + transferRequestDTO.senderID.balance);
    System.out.println("Recipient: " + transferRequestDTO.recipientID.balance);
    System.out.println("Value: " + transferRequestDTO.senderID.name);
    System.out.println("Value: " + transferRequestDTO.recipientID.name);
    System.out.println("-------------------------");
  }
}
