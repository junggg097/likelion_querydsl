package com.example.querydsl;

import com.example.querydsl.entity.Item;
import com.example.querydsl.entity.Shop;
import com.example.querydsl.repo.ItemRepository;
import com.example.querydsl.repo.ShopRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
public class QuerydslQTypeTests {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    // @BeforeEach : 각 테스트 전에 실행할 코드를 작성하는 영역
    @BeforeEach
    public void beforeEach() {
        Shop shopA = shopRepository.save(Shop.builder()
                .name("shopA")
                .description("shop A description")
                .build());
        Shop shopB = shopRepository.save(Shop.builder()
                .name("shopB")
                .description("shop B description")
                .build());

        itemRepository.saveAll(List.of(
                Item.builder()
                        .shop(shopA)
                        .name("itemA")
                        .price(5000)
                        .stock(20)
                        .build(),
                Item.builder()
                        .shop(shopA)
                        .name("itemB")
                        .price(6000)
                        .stock(30)
                        .build(),
                Item.builder()
                        .shop(shopB)
                        .name("itemC")
                        .price(8000)
                        .stock(40)
                        .build(),
                Item.builder()
                        .shop(shopB)
                        .name("itemD")
                        .price(10000)
                        .stock(50)
                        .build()
        ));
    }
}
