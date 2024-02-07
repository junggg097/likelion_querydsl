package com.example.querydsl.repo;

import com.example.querydsl.dto.ItemSearchParams;
import com.example.querydsl.entity.Item;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.example.querydsl.entity.QItem.item;

@Slf4j
@RequiredArgsConstructor
public class ItemQuerydslRepoImpl implements ItemQuerydslRepo {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Item> searchDynamic(ItemSearchParams searchParams) {
        log.info(searchParams.toString());
        return queryFactory.selectFrom(item).fetch();
    }
}