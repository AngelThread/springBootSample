package com.example.adClear.adClear.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(
        indexes = {
                @Index(columnList = "ip", name = "ip_index_to_Ip")
        })
public class Ip {
    @Id
    @GeneratedValue
    private int id;
    private String ip;
}