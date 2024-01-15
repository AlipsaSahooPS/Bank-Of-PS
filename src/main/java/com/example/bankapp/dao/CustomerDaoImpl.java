package com.example.bankapp.dao;

import com.example.bankapp.exceptions.CustomRestServiceException;
import com.example.bankapp.model.Customer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Service
@NoArgsConstructor
public class CustomerDaoImpl implements CustomerDao {
    @Override
    public Customer depositMoney(Customer customer, float money) {
        float balance = customer.getCustomerBalance() + money;
        customer.setCustomerBalance(balance);
        return customer;
    }
    @Override
    public Customer withdrawMoney(Customer customer, float money) throws CustomRestServiceException {
        float balance = customer.getCustomerBalance() - money;
        if (balance < 1000) {
            return null;
        }
        customer.setCustomerBalance(balance);
        return customer;
    }
}
