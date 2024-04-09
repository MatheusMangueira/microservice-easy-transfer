package dtos;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public record UserDTO(
    String id,
    String name,
    String email,
    BigDecimal balance
) {}
