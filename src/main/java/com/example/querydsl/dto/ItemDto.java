package com.example.querydsl.dto;

import lombok.*;
import com.example.querydsl.entity.Item;

@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
@ToString
public class ItemDto {
    private String name;
    private Integer cost;
    private Integer stock;

    public ItemDto(
            String name,
            Integer cost,
            Integer stock
    ) {
        this.name = name;
        this.cost = cost;
        this.stock = stock;
    }
    public static ItemDto fromEntity(Item entity) {
        return new ItemDto(
                entity.getName(),
                entity.getPrice(),
                entity.getStock()
        );
    }
}