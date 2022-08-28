package com.xebia.interview.ps.repository;

import com.xebia.interview.ps.entities.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionHistory, Long> {
}
