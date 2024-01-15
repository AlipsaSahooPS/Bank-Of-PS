package com.example.bankapp.repository;

import com.example.bankapp.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends JpaRepository<Customer,String> {
    @Query("SELECT c.customerPassword FROM Customer c WHERE c.customerId = :customerId")
    String findPasswordByCustomerID(@Param("customerId") String customerId);
}
