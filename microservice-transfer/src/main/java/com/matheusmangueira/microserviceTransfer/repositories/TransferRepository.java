package com.matheusmangueira.microserviceTransfer.repositories;

import com.matheusmangueira.microserviceTransfer.domain.Transfer;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepository extends JpaRepository<Transfer, String> {

}
