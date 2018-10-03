package com.example.adClear.adClear.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(
        indexes = {
                @Index(columnList = "ua", name = "ua_index_to_User")
        })
public class User {
    @Id
    @GeneratedValue
    private int id;
    private String ua;
}