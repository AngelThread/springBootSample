package com.example.adClear.adClear.dao;

import com.example.adClear.adClear.entity.HourlyStats;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;

public interface HourlyStatsDAO extends CrudRepository<HourlyStats, Integer> {



    @Query(nativeQuery = true,value="select * from hourly_stats h where h.time >=:timeBefore and  h.time <=:timeAfter")
    public Iterable<HourlyStats> findByCategory(@Param("timeBefore") Timestamp timeBefore, @Param("timeAfter") Timestamp timeAfter);

}