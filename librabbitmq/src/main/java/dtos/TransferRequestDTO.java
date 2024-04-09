package dtos;


import java.io.Serializable;
import java.math.BigDecimal;


public class TransferRequestDTO implements Serializable {
    public UserDTO senderID;
    public UserDTO recipientID;
    public BigDecimal value;
}