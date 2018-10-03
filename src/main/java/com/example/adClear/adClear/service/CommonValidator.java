package com.example.adClear.adClear.service;

import com.example.adClear.adClear.dao.CustomerDAO;
import com.example.adClear.adClear.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
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
