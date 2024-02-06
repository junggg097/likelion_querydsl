package com.example.querydsl.entity;

import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Shop extends BaseEntity{
    private String name;
    private String description;
}