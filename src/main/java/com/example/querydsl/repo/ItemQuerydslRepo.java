package com.example.querydsl.repo;

import com.example.querydsl.dto.ItemSearchParams;
import com.example.querydsl.entity.Item;

import java.util.List;

public interface ItemQuerydslRepo {
    List<Item> searchDynamic(ItemSearchParams searchParams);
}