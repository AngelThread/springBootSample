package com.example.adClear.adClear.service;

import com.example.adClear.adClear.dao.IpDAO;
import com.example.adClear.adClear.dao.UserDAO;

import com.example.adClear.adClear.dao.HourlyStatsDAO;
import com.example.adClear.adClear.entity.HourlyStat;
import com.example.adClear.adClear.entity.Ip;
import com.example.adClear.adClear.entity.User;
import com.example.adClear.adClear.exception.InvalidBusinessLogicException;
import com.example.adClear.adClear.service.data.ClientRequestData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class HourStatServiceImpl implements HourStatService {
    @Autowired
    private HourlyStatsDAO hourlyStatsDAO;
    @Autowired
    private IpDAO ipDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private CommonValidator commonValidator;

    public Optional<ClientRequestData> handleClientRequest(long clientId, ClientRequestData requestData) {
        checkBusinessLogicValid(clientId, requestData);
        addValidRequestToStats(requestData);
        return Optional.of(requestData);
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
            HourlyStat hourlyStat = buildHourlyStatAsFirstRecordOfHour(requestData.getCustomerID(), requestData.getTimestamp(), 1, 0);
            hourlyStatsDAO.save(hourlyStat);
            log.debug("Client " + requestData.getCustomerID() + " new data created for the hour");
        }
    }

    private void checkBusinessLogicValid(long clientId, ClientRequestData sentObject) {
        List<String> errors = new ArrayList<>();
        if (!checkIfCustomerIdAndClientIdSame(clientId, sentObject)) {
            log.debug("Customer Id:{} in the request and client id:{} in the path are different!", sentObject.getCustomerID(), clientId);
            errors.add("Customer Id:" + clientId + " in the request and client id:" + clientId + " in the path are different!");
        }
        if (!commonValidator.checkCustomerStatusAndExistenceValid(clientId)) {
            log.debug("Customer Id:{} does not exist or not active", clientId);
            errors.add("Customer Id:" + clientId + " idoes not exist or not active!");
        }

        if (!checkIpAddressIsValid(sentObject.getRemoteIP())) {
            log.debug("Customer Id:{} sent request with ip address with in the blacklist!", clientId);
            errors.add("Customer Id:" + clientId + " sent request with ip address with in the blacklist!");
        }

        if (!checkIfUserAgentValid(sentObject.getUserID())) {
            log.debug("Customer Id:{} sent request with user id with in the blacklist!", clientId);
            errors.add("Customer Id:" + clientId + " sent request with user id with in the blacklist!");
        }
        if (!CollectionUtils.isEmpty(errors)) {
            throw new InvalidBusinessLogicException(errors);
        }
    }

    private boolean checkIfUserAgentValid(@NotNull String userID) {
        Optional<User> possibleUser = userDAO.findUserAgent(userID);
        return !possibleUser.isPresent();
    }

    private boolean checkIpAddressIsValid(@NotNull String remoteIP) {
        Optional<Ip> ipInBlackList = ipDAO.findByIp(remoteIP);

        return !ipInBlackList.isPresent();
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
            HourlyStat hourlyStat = buildHourlyStatAsFirstRecordOfHour(clientId, timestamp, 1, 1);
            hourlyStatsDAO.save(hourlyStat);
            log.debug("Client " + clientId + " new data created for the hour");
        }
    }

    private HourlyStat buildHourlyStatAsFirstRecordOfHour(long clientId, Timestamp timestamp, int requestCount, int invalidCount) {
        HourlyStat hourlyStat = new HourlyStat();
        hourlyStat.setRequestCount(requestCount);
        hourlyStat.setInvalidCount(invalidCount);
        hourlyStat.setCustomerId(clientId);
        hourlyStat.setTime(timestamp);
        return hourlyStat;
    }

}
