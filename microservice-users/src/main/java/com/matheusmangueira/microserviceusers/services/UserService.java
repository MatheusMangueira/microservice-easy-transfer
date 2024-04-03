package com.matheusmangueira.microserviceusers.services;

import com.matheusmangueira.microserviceusers.domain.User;
import com.matheusmangueira.microserviceusers.dtos.UserDTO;
import com.matheusmangueira.microserviceusers.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public Page<User> getAllUsers(Pageable pageable) {
    return userRepository.findAll(pageable);
  }

  public User getUserById(String id) {
    return userRepository.findById(id).orElse(null);
  }

  public User createUser(UserDTO user){
    User newUser = new User(user);
    return userRepository.save(newUser);
  }


}
