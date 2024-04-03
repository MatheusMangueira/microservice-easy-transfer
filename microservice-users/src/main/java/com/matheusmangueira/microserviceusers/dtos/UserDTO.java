package com.matheusmangueira.microserviceusers.dtos;

public record UserDTO(
    String name,
    String password,
    String email,
    Double balance
) {}
