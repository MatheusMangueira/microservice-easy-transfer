package dtos;


import java.io.Serializable;
import java.math.BigDecimal;


public class TransferRequestDTO implements Serializable {
    public String senderID;
    public String recipientID;
    public BigDecimal value;
}