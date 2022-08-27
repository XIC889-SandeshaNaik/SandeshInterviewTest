package com.xebia.interview.ps.repository;

import com.xebia.interview.ps.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
