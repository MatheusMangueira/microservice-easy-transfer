package com.matheusmangueira.microserviceusers.services;

import com.matheusmangueira.microserviceusers.domain.User;
import com.matheusmangueira.microserviceusers.dtos.UserDTO;
import com.matheusmangueira.microserviceusers.exceptions.UserAlreadyExistsException;
import com.matheusmangueira.microserviceusers.exceptions.UserNotFoundException;
import com.matheusmangueira.microserviceusers.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
  @Autowired
  private UserRepository userRepository;

  public Page<User> getAllUsers(Pageable pageable) {
    return userRepository.findAll(pageable);
  }

  public User getUserById(String id) {
    return userRepository.findById(id).orElseThrow(
        () -> new UserNotFoundException("User not found")
    );
  }

  public User createUser(UserDTO user) {

    if (userRepository.existsByEmail(user.email())) {
      throw new UserAlreadyExistsException("User exists");
    }

    User newUser = new User(user);

    return userRepository.save(newUser);
  }

  private void updateUserFields(User userToUpdate, UserDTO userDTO) {
    if (userDTO.name() != null) {
      userToUpdate.setName(userDTO.name());
    }

    if (userDTO.email() != null) {
      userToUpdate.setEmail(userDTO.email());
    }

    if (userDTO.balance() != null) {
      userToUpdate.setBalance(userDTO.balance());
    }
  }

  public User updateUser(UserDTO user, String id) {

    User userToUpdate = userRepository.findById(id).orElseThrow(
        () -> new UserNotFoundException("User not found")
    );

    updateUserFields(userToUpdate, user);

    return userRepository.save(userToUpdate);
  }

  public void deleteUser(String id) {

    Optional<User> user = userRepository.findById(id);

    if (user.isEmpty()) {
      throw new UserNotFoundException("User not found with id: " + id);
    }

    userRepository.deleteById(id);
  }

}
