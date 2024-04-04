package com.matheusmangueira.microserviceusers.dtos;

import java.math.BigDecimal;

public record TransferRequestDTO(
    UserDTO sender,
    UserDTO recipient,
    BigDecimal value
) {
}
