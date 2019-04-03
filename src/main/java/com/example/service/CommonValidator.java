package com.example.service;

import com.example.dao.CustomerDAO;
import com.example.entity.Customer;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonValidator {

    @Autowired
    private CustomerDAO customerDAO;

    public static final int ACTIVE_CUSTOMER = 1;

    public boolean checkCustomerStatusAndExistenceValid(long clientId) {
        Optional<Customer> possibleCustomer = customerDAO.findById(clientId);

        return possibleCustomer.isPresent() && possibleCustomer.get().getActive() == ACTIVE_CUSTOMER;
    }
}
