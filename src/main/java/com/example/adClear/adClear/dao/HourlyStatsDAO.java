package com.example.adClear.adClear.dao;

import com.example.adClear.adClear.entity.HourlyStat;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.Optional;

public interface HourlyStatsDAO extends CrudRepository<HourlyStat, Integer> {


    @Query(nativeQuery = true, value = "select * from hourly_stats h where h.time >=:timeBefore and  h.time <=:timeAfter")
    Iterable<HourlyStat> findDataBetweenTwoTime(@Param("timeBefore") Timestamp timeBefore, @Param("timeAfter") Timestamp timeAfter);

    @Query(nativeQuery = true, value = "select * from hourly_stats h where hour(h.time) =:hour")
    Optional<HourlyStat> findByStatsOfTheHour(@Param("hour") int hour);
}