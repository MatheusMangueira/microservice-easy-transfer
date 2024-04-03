package com.matheusmangueira.microserviceusers.infra;

import com.matheusmangueira.microserviceusers.exceptions.UserAlreadyExistsException;
import com.matheusmangueira.microserviceusers.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(UserNotFoundException.class)
  private ResponseEntity<RestErrorMessage> userNotFoundHandler(UserNotFoundException ex) {
    RestErrorMessage errorMessage = new RestErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
  }

  @ExceptionHandler(UserAlreadyExistsException.class)
  private ResponseEntity<RestErrorMessage> userAlreadyExistsHandler(UserAlreadyExistsException ex) {
    RestErrorMessage errorMessage = new RestErrorMessage(HttpStatus.CONFLICT, ex.getMessage());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
  }

}
