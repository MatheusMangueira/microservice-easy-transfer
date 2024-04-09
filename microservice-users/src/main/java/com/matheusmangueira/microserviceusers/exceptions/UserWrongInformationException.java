package com.matheusmangueira.microserviceusers.exceptions;

public class UserWrongInformationException extends RuntimeException {

  public UserWrongInformationException() {
    super("User wrong information" +
        "Please check the information and try again. Example: " +
        "balance: 100.00, " +
        "name: John Doe, " +
        "email: testando@teste.com " +
        "id: 1"
    );
  }

  public UserWrongInformationException(String message) {
    super(message);
  }
}
