package com.matheusmangueira.microserviceTransfer.repositories;

import com.matheusmangueira.microserviceTransfer.domain.Transfer;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransferRepository extends JpaRepository<Transfer, String> {

  @Nonnull
  Optional<Transfer> findById(@Nonnull String id);

}
