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
public class HourlyStat {
    @Id
    private int id;
    private long customerId;
    private Timestamp time;
    private long invalidCount;
    private long requestCount;
}
