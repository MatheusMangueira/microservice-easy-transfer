package com.matheusmangueira.microserviceusers.domain;

import dtos.UserDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Entity(name = "user")
@Table(name = "user_entity")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    @NotBlank
    @Length(min = 3, max = 50)
    private String name;

    @Column(nullable = false)
    @NotBlank
    @Email
    private String email;

    @Column(nullable = false)
    @NotNull
    private BigDecimal balance;


    public User(UserDTO userDTO) {
        this.name = userDTO.name;
        this.email = userDTO.email;
        this.balance = userDTO.balance;

    }
}
