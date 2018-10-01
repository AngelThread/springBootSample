package com.example.adClear.adClear.service;

import com.example.adClear.adClear.dao.IpDAO;
import com.example.adClear.adClear.dao.CustomerDAO;
import com.example.adClear.adClear.dao.UserDAO;

import com.example.adClear.adClear.dao.HourlyStatsDAO;
import com.example.adClear.adClear.entity.Customer;
import com.example.adClear.adClear.entity.HourlyStat;
import com.example.adClear.adClear.entity.Ip;
import com.example.adClear.adClear.entity.User;
import com.example.adClear.adClear.service.data.ClientRequestData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Optional;

@Service
@Slf4j
public class HourStatServiceImpl implements HourStatService {
    public static final int ACTIVE_CUSTOMER = 1;
    @Autowired
    private HourlyStatsDAO hourlyStatsDAO;
    @Autowired
    private CustomerDAO customerDAO;
    @Autowired
    private IpDAO ipDAO;
    @Autowired
    private UserDAO userDAO;

    public Optional<Object> handleClientRequest(long clientId, ClientRequestData requestData) {
        if (!checkBusinessLogicValid(clientId, requestData)) {
            return Optional.empty();
        }
        addValidRequestToStats(requestData);

        return Optional.empty();
    }

    private void addValidRequestToStats(ClientRequestData requestData) {
        Optional<HourlyStat> possibleHourlyStat = hourlyStatsDAO.findByStatsOfTheHour(requestData.getCustomerID(),
                requestData.getTimestamp());
        if (possibleHourlyStat.isPresent()) {
            HourlyStat hourlyStat = possibleHourlyStat.get();
            long requestCount = hourlyStat.getRequestCount();
            hourlyStat.setRequestCount(++requestCount);
            hourlyStatsDAO.save(hourlyStat);
            log.debug("Client " + requestData.getCustomerID() + " valid request count increased");
        } else {
            HourlyStat hourlyStat = buildHourlyStatAsFirstRecordOfHour(requestData.getCustomerID(), requestData.getTimestamp());
            hourlyStatsDAO.save(hourlyStat);
            log.debug("Client " + requestData.getCustomerID() + " new data created for the hour");
        }
    }

    private boolean checkBusinessLogicValid(long clientId, ClientRequestData sentObject) {
        if (!checkIfCustomerIdAndClientIdSame(clientId, sentObject)) {
            log.info("Customer Id:{} in the request and client id:{} in the path are different!", sentObject.getCustomerID(), clientId);
            this.addInvalidRequestToStats(clientId, sentObject.getTimestamp());
            return Boolean.FALSE;
        }
        if (!checkCustomerStatusAndExistenceValid(clientId)) {
            this.addInvalidRequestToStats(clientId, sentObject.getTimestamp());
            log.info("Customer Id:{} does not exist or not active", clientId);
            return Boolean.FALSE;
        }

        if (!checkIpAddressIsValid(sentObject.getRemoteIP())) {
            this.addInvalidRequestToStats(clientId, sentObject.getTimestamp());
            log.info("Customer Id:{} sent request with ip address with in the blacklist!", clientId);
            return Boolean.FALSE;
        }

        if (!checkIfUserAgentValid(sentObject.getUserID())) {
            this.addInvalidRequestToStats(clientId, sentObject.getTimestamp());
            log.info("Customer Id:{} sent request with user id with in the blacklist!", clientId);
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    private boolean checkIfUserAgentValid(@NotNull String userID) {
        Optional<User> possibleUser = userDAO.findById(userID);
        return !possibleUser.isPresent();
    }

    private boolean checkIpAddressIsValid(@NotNull String remoteIP) {
        Optional<Ip> ipInBlackList = ipDAO.findById(remoteIP);

        return !ipInBlackList.isPresent();
    }

    private boolean checkCustomerStatusAndExistenceValid(long clientId) {
        Optional<Customer> possibleCustomer = customerDAO.findById(clientId);

        return possibleCustomer.isPresent() && possibleCustomer.get().getActive() == ACTIVE_CUSTOMER;
    }

    private boolean checkIfCustomerIdAndClientIdSame(long clientId, ClientRequestData sentObject) {
        return clientId == sentObject.getCustomerID();
    }

    @Override
    public synchronized void addInvalidRequestToStats(long clientId, Timestamp timestamp) {
        Optional<HourlyStat> possibleHourlyStat = hourlyStatsDAO.findByStatsOfTheHour(clientId, timestamp);
        if (possibleHourlyStat.isPresent()) {
            HourlyStat hourlyStat = possibleHourlyStat.get();
            long invalidCount = hourlyStat.getInvalidCount();
            long requestCount = hourlyStat.getRequestCount();
            hourlyStat.setInvalidCount(++invalidCount);
            hourlyStat.setRequestCount(++requestCount);
            hourlyStatsDAO.save(hourlyStat);
            log.debug("Client " + clientId + " invalid request count increased");
        } else {
            HourlyStat hourlyStat = buildHourlyStatAsFirstRecordOfHour(clientId, timestamp);
            hourlyStatsDAO.save(hourlyStat);
            log.debug("Client " + clientId + " new data created for the hour");
        }
    }

    private HourlyStat buildHourlyStatAsFirstRecordOfHour(long clientId, Timestamp timestamp) {
        HourlyStat hourlyStat = new HourlyStat();
        hourlyStat.setRequestCount(1);
        hourlyStat.setInvalidCount(1);
        hourlyStat.setCustomerId(clientId);
        hourlyStat.setTime(timestamp);
        return hourlyStat;
    }

}
