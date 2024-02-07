package com.example.querydsl.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Item extends BaseEntity{
    private String name;
    private String description;
    private Integer price;
    private Integer stock;

    @ManyToOne(fetch = FetchType.LAZY)
    private Shop shop;

    @Override
    public String toString() {
        return "Item{" +
                "id=" + getId() +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                "}";
    }
}