package com.example.adClear.adClear.controller;

import com.example.adClear.adClear.controller.dto.ClientRequestDto;
import com.example.adClear.adClear.dao.CustomerDAO;
import com.example.adClear.adClear.dao.HourlyStatsDAO;
import com.example.adClear.adClear.entity.HourlyStats;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Slf4j
@RestController
public class MainController {
    @Autowired
    private CustomerDAO customerDAO;

    @Autowired
    private HourlyStatsDAO hourlyStatsDAO;

    @RequestMapping("/clients")
    public String clients(@RequestParam(value = "id", defaultValue = "1") String id) {
//
//        System.out.println("Called!");
//
//        Optional<Customer> customer = customerDAO.findById(Long.valueOf(id));
//        System.out.println("Called!");
//        String returnEd = customer.isPresent() ? customer.get().getName() : "Id:" + id;
        return "Hello World";
    }

    @PostMapping("/client/request")
    public String clientRequest(Object object) {

        log.info("Request sent!");

        System.out.println(object);

        Timestamp timeBefore = Timestamp.valueOf(LocalDateTime.now().minusHours(2));
        Timestamp timeAfter = Timestamp.valueOf(LocalDateTime.now());
        log.info("timeBefore:" + timeBefore);
        log.info("timeAfter:" + timeAfter);

        Iterable<HourlyStats> byCategory = hourlyStatsDAO.findByCategory(timeBefore,
                timeAfter);
        log.info("byCategory.toString()" + byCategory.toString());
//
//        Optional<Customer> customer = customerDAO.findById(Long.valueOf(id));
//        System.out.println("Called!");
//        String returnEd = customer.isPresent() ? customer.get().getName() : "Id:" + id;
        return "Hello World";
    }
}
