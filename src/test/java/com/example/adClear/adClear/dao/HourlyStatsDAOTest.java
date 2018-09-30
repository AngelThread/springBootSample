package com.example.adClear.adClear.dao;

import com.example.adClear.adClear.entity.Customer;
import com.example.adClear.adClear.entity.HourlyStats;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.Optional;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class HourlyStatsDAOTest {
    @Autowired
    private HourlyStatsDAO hourlyStatsDAO;
    @Autowired
    private CustomerDAO customerDAO;
    private Long customerId = 3l;

    @Before
    public void setup() {
        Customer customer = new Customer();
        customer.setActive(1);
        customer.setId(customerId);
        customer.setName("Customer " + customerId);
        customerDAO.save(customer);

    }

    @After
    public void tearDown() {
        hourlyStatsDAO.deleteAll();
        customerDAO.deleteAll();
    }

    @Test
    public void testInsertFunctionality() {

        int id = 101;
        HourlyStats sample = new HourlyStats();
        sample.setCustomerId(customerId);
        sample.setId(id);
        sample.setInvalidCount(3l);
        sample.setRequestCount(103l);
        sample.setTime(new Timestamp(1500000000));

        hourlyStatsDAO.save(sample);
        Optional<HourlyStats> hourlyStatsById = hourlyStatsDAO.findById(id);
        assertTrue(hourlyStatsById.isPresent());
        assertTrue(hourlyStatsById.get().getId() == id);
    //    assertTrue(hourlyStatsById.get().getId() == 253253);

    }
}
