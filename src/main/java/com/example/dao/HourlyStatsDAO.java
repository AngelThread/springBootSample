package com.example.dao;

import com.example.entity.HourlyStat;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Optional;

public interface HourlyStatsDAO extends CrudRepository<HourlyStat, Integer> {

    @Query(nativeQuery = true, value = "select * from hourly_stats h where h.customer_id=:clientId  and  TRUNC(h.time) =:date ")
    Iterable<HourlyStat> findDailyRequests(@Param("clientId") long clientId, @Param("date") LocalDate date);

    @Query(nativeQuery = true, value = "select * from hourly_stats h where hour(h.time) =hour(:timestamp) and TRUNC(h.time) = TRUNC(:timestamp) and h.customer_id=:clientId")
    Optional<HourlyStat> findByStatsOfTheHour(@Param("clientId") long clientId, @Param("timestamp") Timestamp timestamp);
}