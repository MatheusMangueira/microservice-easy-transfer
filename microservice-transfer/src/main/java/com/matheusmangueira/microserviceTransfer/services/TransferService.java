package com.matheusmangueira.microserviceTransfer.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.matheusmangueira.microserviceTransfer.domain.Transfer;
import com.matheusmangueira.microserviceTransfer.constants.StatusTransfer;
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

@Service
public class TransferService {
  @Autowired
  private TransferRepository transferRepository;

  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private RabbitTemplate rabbitTemplate;

  public void transferAccount(BigDecimal value, UserDTO sender, UserDTO recipient) {

    try {
      sender.balance = sender.balance.subtract(value);
      recipient.balance = recipient.balance.add(value);

      sendMessage(StatusTransfer.COMPLETED, value, sender, recipient);

      Transfer transfer = new Transfer(sender, recipient, value);
      transferRepository.save(transfer);

    } catch (Exception e) {
      sendMessage(StatusTransfer.CANCELED, value, sender, recipient);
      throw new RuntimeException("Error to send message to queue");
    }

  }

  private void sendMessage(String status, BigDecimal value, UserDTO sender, UserDTO recipient) {
    try {
      Object[] data = new Object[]{status, value, sender, recipient};
      String messageJson = objectMapper.writeValueAsString(data);

      rabbitTemplate.convertAndSend("notification-row", messageJson);
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
