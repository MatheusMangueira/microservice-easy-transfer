package com.matheusmangueira.microserviceusers.rabbit.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.matheusmangueira.microserviceusers.repositories.UserRepository;
import com.matheusmangueira.microserviceusers.services.UserService;
import dtos.TransferRequestDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserConsumer {

  @Autowired
  private UserService userService;

  @RabbitListener(queues = "transferUserBack-row")
  public void consumer(String message) throws JsonProcessingException, InterruptedException {
    TransferRequestDTO transferRequestDTO = new ObjectMapper().readValue(message, TransferRequestDTO.class);

    userService.updateTransfer(
        transferRequestDTO.senderID.id,
        transferRequestDTO.recipientID.id,
        transferRequestDTO.senderID.balance,
        transferRequestDTO.recipientID.balance
    );

    System.out.println("Transfer: " + message);
    System.out.println("Sender: " + transferRequestDTO.senderID.balance);
    System.out.println("Recipient: " + transferRequestDTO.recipientID.balance);
    System.out.println("Value: " + transferRequestDTO.senderID.name);
    System.out.println("Value: " + transferRequestDTO.recipientID.name);
    System.out.println("-------------------------");
  }
}
