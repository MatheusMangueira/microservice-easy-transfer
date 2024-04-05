package com.matheusmangueira.microserviceTransfer.services;

import com.matheusmangueira.microserviceTransfer.repositories.TransferRepository;
import dtos.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class  TransferService {
  @Autowired
  private TransferRepository transferRepository;


  public void transfer(BigDecimal value, UserDTO sender, UserDTO recipient) {



    BigDecimal subtract = sender.balance().subtract(value);
    BigDecimal add = recipient.balance().add(value);
  }

  public void nofitication(String status) {
    System.out.println("Transfer " + status);
  }


}
