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

        int id = 102;
        HourlyStat sample = createSampleHourlyStat(id);
        hourlyStatsDAO.save(sample);
        Optional<HourlyStat> byStatsOfTheHour = hourlyStatsDAO.findByStatsOfTheHour((TIMESTAMP.toLocalDateTime().getHour()));
        assertTrue(byStatsOfTheHour.isPresent());
        assertTrue(byStatsOfTheHour.get().getId() == id);
    }

    private HourlyStat createSampleHourlyStat(int id) {
        HourlyStat sample = new HourlyStat();
        sample.setCustomerId(customerId);
        sample.setId(id);
        sample.setInvalidCount(3l);
        sample.setRequestCount(103l);
        sample.setTime(TIMESTAMP);
        return sample;
    }
}
