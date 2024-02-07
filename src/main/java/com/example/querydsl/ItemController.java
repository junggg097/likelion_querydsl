package com.example.querydsl;

import com.example.querydsl.dto.ItemDto;
import com.example.querydsl.dto.ItemSearchParams;
import com.example.querydsl.repo.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ItemController {
    private final ItemRepository itemRepository;

    @GetMapping("search")
    public List<ItemDto> search(
            // Query Parameter를 받아온다
            // /search?name=name&priceFloor=1&priceCeil=10
            ItemSearchParams searchParams
    ) {
        return itemRepository.searchDynamic(searchParams)
                .stream()
                .map(ItemDto::fromEntity)
                .collect(Collectors.toList());
    }

}