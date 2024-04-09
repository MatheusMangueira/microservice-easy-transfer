package dtos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public class UserDTO implements Serializable {
  public String id;
  public String name;
  public String email;
  public BigDecimal balance;
}
