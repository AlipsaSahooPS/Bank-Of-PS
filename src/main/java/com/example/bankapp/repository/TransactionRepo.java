package com.example.bankapp.repository;

import com.example.bankapp.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction,String> {
    @Query("SELECT t FROM Transaction t WHERE t.customer.customerId = :customerId ORDER BY t.transactionDate DESC")
    List<Transaction> findTransactions(@Param("customerId") String customerId);
}
