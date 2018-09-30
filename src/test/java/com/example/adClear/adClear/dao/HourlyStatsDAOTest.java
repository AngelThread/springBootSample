package com.example.adClear.adClear.dao;

import com.example.adClear.adClear.entity.Customer;
import com.example.adClear.adClear.entity.HourlyStat;
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
    public static final Timestamp TIMESTAMP = new Timestamp(1500000000);
    public static final long INVALID_COUNT = 3l;
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
    public void testFindByHour() {
        HourlyStat sample = createSampleHourlyStat();
        hourlyStatsDAO.save(sample);
        Optional<HourlyStat> byStatsOfTheHour = hourlyStatsDAO.findByStatsOfTheHour(customerId,TIMESTAMP);
        assertTrue(byStatsOfTheHour.isPresent());
        assertTrue(byStatsOfTheHour.get().getInvalidCount() == INVALID_COUNT);
    }

    private HourlyStat createSampleHourlyStat() {
        HourlyStat sample = new HourlyStat();
        sample.setCustomerId(customerId);
        sample.setInvalidCount(INVALID_COUNT);
        sample.setRequestCount(103l);
        sample.setTime(TIMESTAMP);
        return sample;
    }
}
