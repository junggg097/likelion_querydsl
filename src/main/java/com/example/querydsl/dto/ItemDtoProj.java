package com.example.querydsl.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
@ToString
public class ItemDtoProj {
    private String name;
    private Integer price;
    private Integer stock;

    // QDto를 만들고, 그 생성자를 사용해 데이터를
    // Projection 할 수 있다.
    @QueryProjection
    public ItemDtoProj(
            String name,
            Integer price,
            Integer stock
    ) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }
    // Querydsl과 DTO의 결합력(Coupling)이 강해진다.
    // Querydsl에 대한 의존도가 높아진다.
}