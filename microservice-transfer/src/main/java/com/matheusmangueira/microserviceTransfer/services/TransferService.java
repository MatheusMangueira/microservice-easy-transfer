package com.matheusmangueira.microserviceTransfer.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.matheusmangueira.microserviceTransfer.domain.Transfer;
import com.matheusmangueira.microserviceTransfer.exceptions.TransferFailedException;
import com.matheusmangueira.microserviceTransfer.repositories.TransferRepository;
import dtos.UserDTO;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class TransferService {
  @Autowired
  private TransferRepository transferRepository;

  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private RabbitTemplate rabbitTemplate;

  public void transferAccount(BigDecimal value, UserDTO senderID, UserDTO recipientID) {

    try {
      senderID.balance = senderID.balance.subtract(value);
      recipientID.balance = recipientID.balance.add(value);

      sendMessage(value, senderID, recipientID);

      Transfer transfer = new Transfer(senderID, recipientID, value);
      transferRepository.save(transfer);

    } catch (Exception e) {
      throw new RuntimeException("Error to send message to queue");
    }

  }

  private void sendMessage(BigDecimal value, UserDTO senderID, UserDTO recipientID) {
    try {
      Map<String, Object> transferData = new HashMap<>();
      transferData.put("value", value);
      transferData.put("senderID", senderID);
      transferData.put("recipientID", recipientID);
      String messageJson = objectMapper.writeValueAsString(transferData);
      rabbitTemplate.convertAndSend("transferUserBack-row", messageJson);

    } catch (JsonProcessingException e) {
      throw new TransferFailedException("Failed to serialize transfer data", e);

    } catch (AmqpException e) {
      throw new TransferFailedException("Failed to send message to queue", e);
    }
  }

  public Page<Transfer> getAllTransfers(Pageable pageable) {
    return transferRepository.findAll(pageable);
  }

  public Transfer getTransferById(String id) {
    return transferRepository.findById(id).orElseThrow(
        () -> new TransferFailedException("Transfer not found")
    );
  }

}
