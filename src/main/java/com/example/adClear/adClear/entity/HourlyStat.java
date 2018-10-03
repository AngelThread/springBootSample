package com.example.adClear.adClear.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@Table(name = "hourly_stats",
        indexes = {
                @Index(columnList = "customerId,time", name = "customerId_index_to_HourlyStat,time_index_to_HourlyStat")
        })
public class HourlyStat {
    @Id
    @GeneratedValue
    private int id;
    private long customerId;
    private Timestamp time;
    private long invalidCount;
    private long requestCount;
}
