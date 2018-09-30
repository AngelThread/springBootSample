package com.example.adClear.adClear.dao;

import com.example.adClear.adClear.entity.HourlyStat;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.Optional;

public interface HourlyStatsDAO extends CrudRepository<HourlyStat, Integer> {

    //TODO add customer Id as filter
    @Query(nativeQuery = true, value = "select * from hourly_stats h where h.time >=:timeBefore and  h.time <=:timeAfter and h.customer_id=:clientId")
    Iterable<HourlyStat> findDataBetweenTwoTime(@Param("timeBefore") Timestamp timeBefore, @Param("timeAfter") Timestamp timeAfter);

    @Query(nativeQuery = true, value = "select * from hourly_stats h where hour(h.time) =hour(:timestamp) and TRUNC(h.time) = TRUNC(:timestamp) and h.customer_id=:clientId")
    Optional<HourlyStat> findByStatsOfTheHour(@Param("clientId") long clientId, @Param("timestamp") Timestamp timestamp);
}