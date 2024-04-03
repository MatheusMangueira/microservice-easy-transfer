package com.matheusmangueira.microserviceusers.domain;

import com.matheusmangueira.microserviceusers.dtos.UserDTO;
import jakarta.persistence.*;
import lombok.*;

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
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Double balance;


    public User(UserDTO userDTO) {
        this.name = userDTO.name();
        this.email = userDTO.email();
        this.password = userDTO.password();
        this.balance = userDTO.balance();

    }
}
