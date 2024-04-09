package com.matheusmangueira.microserviceTransfer.controllers;


import com.matheusmangueira.microserviceTransfer.domain.Transfer;
import com.matheusmangueira.microserviceTransfer.services.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/transfers")
@RestController
public class TransferController {

  @Autowired
  private TransferService transferService;


  @GetMapping("/all")
  public ResponseEntity<List<Transfer>> getAllTransfers(
      @RequestParam(defaultValue = "1")
      Integer page, @RequestParam(defaultValue = "10") Integer limit) {

    Pageable pageable = PageRequest.of(page - 1, limit);
    Page<Transfer> transfers = transferService.getAllTransfers(pageable);
    List<Transfer> transfersList = transfers.getContent();

    return ResponseEntity.status(HttpStatus.OK).body(transfersList);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Transfer> getTransferById(@PathVariable String id) {

    Transfer transfer = transferService.getTransferById(id);

    return ResponseEntity.status(HttpStatus.OK).body(transfer);
  }

}
