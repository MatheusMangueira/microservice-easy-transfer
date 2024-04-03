package com.matheusmangueira.microserviceusers.controllers;

import com.matheusmangueira.microserviceusers.domain.User;
import com.matheusmangueira.microserviceusers.dtos.UserDTO;
import com.matheusmangueira.microserviceusers.exceptions.UserAlreadyExistsException;
import com.matheusmangueira.microserviceusers.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/users")
@RestController
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping("/all")
  public ResponseEntity<List<User>> getAllUsers(@RequestParam(defaultValue = "1")
                                                Integer page, @RequestParam(defaultValue = "10") Integer limit) {

    Pageable pageable = PageRequest.of(page - 1, limit);
    Page<User> usersPage = userService.getAllUsers(pageable);
    List<User> users = usersPage.getContent();

    return ResponseEntity.status(HttpStatus.OK).body(users);
  }

  @GetMapping("/{id}")
  public ResponseEntity<User> getUserById(@PathVariable String id) {

    User user = userService.getUserById(id);

    return ResponseEntity.status(HttpStatus.OK).body(user);
  }

  @PostMapping
  public ResponseEntity<User> createUser(@RequestBody UserDTO user) {
      User newUser = userService.createUser(user);
      return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
  }

  @PutMapping("/{id}")
  public ResponseEntity<User> updateUser(@RequestBody UserDTO user, @PathVariable String id) {

    User updatedUser = userService.updateUser(user, id);

    return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable String id) {
    userService.deleteUser(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
