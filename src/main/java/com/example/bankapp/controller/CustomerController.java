package com.example.bankapp.controller;

import com.example.bankapp.exceptions.CustomRestServiceException;
import com.example.bankapp.model.Customer;
import com.example.bankapp.model.Transaction;
import com.example.bankapp.service.CustomerService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
@SecurityRequirement(name="bankControllerSecurity")
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/createCustomer")
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer) throws CustomRestServiceException {
        try {
            return new ResponseEntity<>(customerService.createCustomer(customer), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new CustomRestServiceException("Error in createCustomer is:", e);
        }
    }

    @PutMapping("/deposit/{customerId}/{money}")
    public ResponseEntity<Float> depositMoney(@PathVariable String customerId, @PathVariable float money) throws CustomRestServiceException {
        try{
            System.out.println("ONE");
            return ResponseEntity.ok(customerService.depositMoney(customerId, money));
        } catch (Exception e){
            throw new CustomRestServiceException("Error in depositMoney is: ", e);
        }
    }

    @PutMapping("/withdraw/{customerId}/{money}")
    public ResponseEntity<Float> withdrawMoney(@PathVariable String customerId, @PathVariable float money) throws CustomRestServiceException {
        try {

            return ResponseEntity.ok(customerService.withdrawMoney(customerId, money));
        } catch (Exception e){
            throw new CustomRestServiceException("Error in withdrawMoney is: ", e);
        }
    }
   @Hidden
    @GetMapping("/getSingleCustomer/{customerId}")
    public ResponseEntity<Customer> getSingleCustomer(@PathVariable String customerId) throws CustomRestServiceException {
        try {
            return ResponseEntity.ok(customerService.getSingleCustomerById(customerId));
        } catch (Exception e){
            throw new CustomRestServiceException("Error in getSingleCustomer is: ", e);
        }
    }


    @Hidden
    @GetMapping("/getAllCustomers")
    public ResponseEntity<List<Customer>> getAllCustomers() throws CustomRestServiceException {
        try {
            return ResponseEntity.ok(customerService.getAllCustomers());
        } catch (Exception e) {
            throw new CustomRestServiceException("Error in getAllCustomers is: ", e);
        }
    }
 @Hidden
    @GetMapping("/checkCustomer/{customerId}/{password}")
    public ResponseEntity<Boolean> checkCustomer(@PathVariable String customerId, @PathVariable String password) throws CustomRestServiceException {
        try {
            System.out.println(customerService.checkCustomer(customerId, password));
            return ResponseEntity.ok(customerService.checkCustomer(customerId, password));
        } catch (Exception e) {
            throw new CustomRestServiceException("Error in checkCustomer is: ", e);
            
        }
    }
@Hidden
    @DeleteMapping("/deleteCustomer/{customerId}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable String customerId) throws CustomRestServiceException {
        try {
            return ResponseEntity.ok(customerService.deleteCustomer(customerId));
        } catch (Exception e){
            throw new CustomRestServiceException("Error in deleteCustomer is: ", e);
        }
    }
@Hidden
    @GetMapping("/getAllTransactions/{customerId}")
    public ResponseEntity<List<Transaction>> getLastTransaction(@PathVariable String customerId) throws CustomRestServiceException {
        try {
            return ResponseEntity.ok(customerService.getLastTransaction(customerId));
        } catch (Exception e){
            throw new CustomRestServiceException("Error in getAllTransactions is: ", e);
        }
    }
}
