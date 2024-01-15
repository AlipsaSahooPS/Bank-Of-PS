package com.example.bankapp.service;

import com.example.bankapp.exceptions.CustomRestServiceException;
import com.example.bankapp.exceptions.CustomerNotFoundException;
import com.example.bankapp.model.Customer;
import com.example.bankapp.model.Transaction;

import java.util.List;

public interface CustomerService {
    Customer createCustomer(Customer customer) throws CustomRestServiceException;
    float depositMoney(String customerId, float money) throws CustomerNotFoundException;
    float withdrawMoney(String customerId, float money) throws CustomerNotFoundException, CustomRestServiceException;
    List<Transaction> getLastTransaction(String customerId) throws CustomerNotFoundException;
    Customer getSingleCustomerById(String customerId);
    List<Customer> getAllCustomers() throws CustomRestServiceException;
    Customer deleteCustomer(String customerId) throws Exception;
    Boolean checkCustomer(String customerId, String password);
}
