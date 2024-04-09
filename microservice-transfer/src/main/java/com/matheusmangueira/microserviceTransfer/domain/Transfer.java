package com.matheusmangueira.microserviceTransfer.domain;

import dtos.TransferRequestDTO;
import dtos.UserDTO;
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
  private String recipientID;

  @Column(nullable = false)
  @NotNull
  private BigDecimal value;

  public Transfer(UserDTO sender, UserDTO recipient, BigDecimal value) {
    this.senderID = sender.id;
    this.recipientID = recipient.id;
    this.value = value;
  }

}
