package com.example.adClear.adClear.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@Table(name = "hourly_stats")
public class HourlyStats {
    @Id
    private Integer id;
    private Long customerId;
    private Timestamp time;
    private Long invalidCount;
    private Long requestCount;
}
