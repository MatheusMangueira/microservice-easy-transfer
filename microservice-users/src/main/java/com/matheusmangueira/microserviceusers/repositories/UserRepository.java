package com.matheusmangueira.microserviceusers.repositories;

import com.matheusmangueira.microserviceusers.domain.User;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

  //@Nonnull
 // Optional<User> findById(@Nonnull String id);

  boolean existsByEmail(@Nonnull String email);
}

