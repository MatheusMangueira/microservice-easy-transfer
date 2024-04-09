package com.matheusmangueira.microserviceTransfer.domain;

import dtos.TransferRequestDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Entity(name = "transfer")
@Table(name = "transfer_entity")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class Transfer {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(nullable = false)
  @NotBlank
  private String senderID;

  @Column(nullable = false)
  @NotBlank
  private String receiverID;

  @Column(nullable = false)
  @NotNull
  private BigDecimal value;

  public Transfer(TransferRequestDTO TransferRequestDTO ) {
    this.senderID = TransferRequestDTO.senderID.id();
    this.receiverID =TransferRequestDTO.recipientID.id();
    this.value = TransferRequestDTO.value;
  }

}
