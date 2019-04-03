package com.example.dao;

import com.example.entity.Customer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerDAOTest {
    private long customerId = 3l;
    @Autowired
    private CustomerDAO customerDAO;

    @Before
    public void setUp() throws Exception {
        Customer customer = new Customer();
        customer.setActive(1);
        customer.setId(customerId);
        customer.setName("Customer " + customerId);
        customerDAO.save(customer);
    }

    @After
    public void tearDown() throws Exception {
        customerDAO.deleteAll();
    }

    @Test
    public void findById() {
        Optional<Customer> byId = customerDAO.findById(customerId);
        assertEquals(byId.get().getId(), customerId);
    }
}