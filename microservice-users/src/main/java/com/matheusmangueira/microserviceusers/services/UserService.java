package com.matheusmangueira.microserviceusers.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matheusmangueira.microserviceusers.constants.UserConstants;
import com.matheusmangueira.microserviceusers.domain.User;
import com.matheusmangueira.microserviceusers.exceptions.TransferFailedException;
import com.matheusmangueira.microserviceusers.exceptions.UserAlreadyExistsException;
import com.matheusmangueira.microserviceusers.exceptions.UserNotFoundException;
import com.matheusmangueira.microserviceusers.exceptions.UserWrongInformationException;
import com.matheusmangueira.microserviceusers.repositories.UserRepository;
import dtos.TransferRequestDTO;
import dtos.UserDTO;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.Optional;

@Service
public class UserService {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RabbitTemplate rabbitTemplate;

  @Autowired
  private ObjectMapper objectMapper;

  public Page<User> getAllUsers(Pageable pageable) {
    return userRepository.findAll(pageable);
  }

  public User getUserById(String id) {
    return userRepository.findById(id).orElseThrow(
        () -> new UserNotFoundException("User not found")
    );
  }

  public User createUser(UserDTO user) {

    if (userRepository.existsByEmail(user.email)) {
      throw new UserAlreadyExistsException("User exists");
    }

    User newUser = new User(user);

    return userRepository.save(newUser);
  }

  private void updateUserFields(User userToUpdate, UserDTO userDTO) {
    if (userDTO.name != null) {
      userToUpdate.setName(userDTO.name);
    }

    if (userDTO.email != null) {
      userToUpdate.setEmail(userDTO.email);
    }

    if (userDTO.balance != null) {
      userToUpdate.setBalance(userDTO.balance);
    }
  }

  public User updateUser(UserDTO user, String id) {

    User userToUpdate = userRepository.findById(id).orElseThrow(
        () -> new UserNotFoundException("User not found")
    );

    updateUserFields(userToUpdate, user);

    return userRepository.save(userToUpdate);
  }

  public void deleteUser(String id) {

    Optional<User> user = userRepository.findById(id);

    if (user.isEmpty()) {
      throw new UserNotFoundException("User not found with id: " + id);
    }

    userRepository.deleteById(id);
  }

  public void transfer(String nameRow, TransferRequestDTO transferRequestDTO) {
    validateTransferRequest(transferRequestDTO);

    try {
      String messageJson = objectMapper.writeValueAsString(transferRequestDTO);
      rabbitTemplate.convertAndSend(nameRow, messageJson);

    } catch (Exception e) {
      throw new RuntimeException("Error to send message to queue");
    }

  }

  private void validateTransferRequest(TransferRequestDTO transferRequestDTO) {
    User sender = userRepository.findById(transferRequestDTO.senderID.id)
        .filter(user ->
            user.getEmail().equals(transferRequestDTO.senderID.email
            ) && user.getName().equals(transferRequestDTO.senderID.name
            ) && user.getBalance().equals(transferRequestDTO.senderID.balance
            )
        )
        .orElseThrow(() -> new UserWrongInformationException("Invalid sender information. " +
            "Please check the information and try again. Example: " +
            "balance: 100.00, " +
            "name: John Doe, " +
            "email: testando@teste.com " +
            "id: 1"));


    User recipient = userRepository.findById(transferRequestDTO.recipientID.id)
        .filter(user ->
            user.getEmail().equals(transferRequestDTO.recipientID.email)
                && user.getName().equals(transferRequestDTO.recipientID.name)
                && user.getBalance().equals(transferRequestDTO.recipientID.balance
            )
        )
        .orElseThrow(() -> new UserWrongInformationException("Invalid recipient information. " +
            "Please check the information and try again. Example: " +
            "balance: 100.00, " +
            "name: John Doe, " +
            "email: testando@teste.com " +
            "id: 1"));

    BigDecimal value = transferRequestDTO.value;

    if (value != null && value.compareTo(sender.getBalance()) > 0) {
      throw new IllegalArgumentException("Invalid transfer request");
    }
  }

  public void updateTransfer(
      String senderID,
      String recipientID,
      BigDecimal balanceSender,
      BigDecimal balanceRecipient,
      String senderName,
      String nameRecipient,
      BigDecimal value
  ) {

    try {
      User userSender = userRepository.findById(senderID).orElseThrow(
          () -> new UserNotFoundException("User sender not found")
      );

      User userRecipient = userRepository.findById(recipientID).orElseThrow(
          () -> new UserNotFoundException("User recipient not found")
      );

      userSender.setBalance(balanceSender);
      userRecipient.setBalance(balanceRecipient);

      userRepository.save(userSender);
      userRepository.save(userRecipient);

      TransactionApproved(
          UserConstants.USER_COMPLETED +
              " de: " + senderName
              + " para: " + nameRecipient
              + " no valor de: " + value);

    } catch (Exception e) {
      TransactionApproved(UserConstants.USER_CANCELED);
      throw new TransferFailedException("Error to update transfer");
    }

  }

  public void TransactionApproved(String message) {

    try {
      String messageJson = objectMapper.writeValueAsString(message);
      rabbitTemplate.convertAndSend("notification-row", messageJson);

    } catch (Exception e) {
      throw new RuntimeException("Error to send message to queue");
    }
  }


}
