package com.example.querydsl;

import com.example.querydsl.entity.Item;
import com.example.querydsl.entity.QItem;
import com.example.querydsl.entity.Shop;
import com.example.querydsl.repo.ItemRepository;
import com.example.querydsl.repo.ShopRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.querydsl.core.QueryResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

import java.util.List;

import static com.example.querydsl.entity.QItem.item;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
public class QuerydslQueryTests {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private JPAQueryFactory queryFactory;

    // @BeforeEach: 각 테스트 전에 실행할 코드를 작성하는 영역
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
                        .build(),
                Item.builder()
                        .name("itemE")
                        .price(11000)
                        .stock(10)
                        .build(),
                Item.builder()
                        .price(10500)
                        .stock(25)
                        .build()
        ));
    }

    @Test
    public void fetch() {
        // fetch(): 단순하게 전체 조회
        List<Item> foundList = queryFactory
                // SELECT FROM 절
                .selectFrom(item)
                // 결과를 리스트 형태로 조회
                .fetch();
        assertEquals(6, foundList.size());

        // fetchOne(): 하나만 조회하려고 시도
        Item found = queryFactory
                .selectFrom(item)
                .where(item.id.eq(1L))
                // 하나만 조회
                .fetchOne();
        assertEquals(1L, found.getId());

        found = queryFactory
                .selectFrom(item)
                .where(item.id.eq(0L))
                // 없을 경우 null
                .fetchOne();
        assertNull(found);

        assertThrows(Exception.class, () -> {
            queryFactory.selectFrom(item)
                    // 2개 이상일 경우 Exception
                    .fetchOne();
        });

        // fetchFirst(): 첫번째 결과 또는 null
        found = queryFactory
                .selectFrom(item)
                // LIMIT 1 -> fetchOne();
                .fetchFirst();
        assertNotNull(found);

        // offset limit
        foundList = queryFactory
                .selectFrom(item)
                .offset(3)
                .limit(2)
                .fetch();
        for (Item find: foundList) {
            System.out.println(find.getId());
        }

        // fetchCount(): 결과의 갯수 반환 (deprecated)
        long count = queryFactory
                .selectFrom(item)
                .fetchCount();
        assertEquals(6, count);

        // fetchResults(): 결과 및 count + offset + limit 정보 반환 (deprecated)
        QueryResults<Item> results = queryFactory
                .selectFrom(item)
                .offset(3)
                .limit(2)
                .fetchResults();
        System.out.println(results.getTotal());
        System.out.println(results.getOffset());
        System.out.println(results.getLimit());
        // 실제 내용은 getResults()
        foundList = results.getResults();
    }

    @Test
    public void sort() {
        itemRepository.saveAll(List.of(
                Item.builder()
                        .name("itemF")
                        .price(6000)
                        .stock(40)
                        .build(),
                Item.builder()
                        .price(6000)
                        .stock(40)
                        .build())
        );
        List<Item> foundList = queryFactory
                // SELECT i FROM Item i
                .selectFrom(item)
                // item.(속성).(순서)를 ORDER BY 넣을 순서대로
                // ORDER BY i.price ASC
                .orderBy(
                        // item.price asc
                        item.price.asc(),
                        item.stock.desc(),
                        // null이 먼저냐 나중이냐
                        item.name.asc().nullsLast()
//                        item.name.asc().nullsFirst()
                )
                .fetch();

        for (Item found: foundList) {
            System.out.printf("%s: %d (%d)%n", found.getName(), found.getPrice(), found.getStock());
        }
    }

    @Test
    public void where() {
        // item.(속성).(조건)
        // equals ( = )
        item.name.eq("itemA");
        // not equals ( != )
        item.name.ne("itemB");
        // equals -> not (!( = ))
        item.name.eq("itemC").not();

        // is null
        item.name.isNull();
        // is not null
        item.name.isNotNull();
        item.name.isNotEmpty();

        // < <= >= >
        item.price.lt(6000);
        item.price.loe(6000);
        item.price.goe(8000);
        item.price.gt(7000);

        item.price.between(5000, 10000);
        item.price.in(5000, 6000, 7000, 8000);

        // like, contains, startsWith, endsWith
        // like는 SQL 문법을 따른다.
        item.name.like("%item_");
        // contains: arg -> %arg%
        item.name.contains("item");
        // startsWith, endsWith -> arg%, %arg
        item.name.startsWith("item");
        item.name.endsWith("A");

        // 시간 관련
        // 지금으로부터 5일전 보다 이후
        item.createdAt.after(LocalDateTime.now().minusDays(5));
        // 지금으로부터 5일전 보다 이전
        item.createdAt.before(LocalDateTime.now().minusDays(5));

        List<Item> foundItems = queryFactory
                .selectFrom(item)
                // where에 복수개 넣어주면, 전부 만족 (AND로 엮임)
                .where(
                        item.name.isNotNull(),
                        item.price.lt(8000),
                        item.stock.gt(20)
                )
                .fetch();

        for (Item found: foundItems) {
            System.out.printf("%s: %d (%d)%n", found.getName(), found.getPrice(), found.getStock());
        }
    }


    @Test
    public void andOr() {
        List<Item> foundItems = queryFactory
                .selectFrom(item)
                .fetch();

        for (Item found: foundItems) {
            System.out.println(found);
        }

        foundItems = queryFactory
                .selectFrom(item)
                // 가격이 6000 이하 또는 9000 이상
                // .and() 또는 .or()를 연쇄 호출할 수 있다. (method chaining)
                .where(
                        // [{item.price <= 6000
                        item.price.loe(6000)
                                // OR (item.price >= 9000)}
                                .or(item.price.goe(9000))
                                // OR (item.stock in (20, 30, 40)]
                                .or(item.stock.in(20, 30, 40))
                                // AND item.name is not null
                                .and(item.name.isNotNull())
                )
                .fetch();

        for (Item found: foundItems) {
            System.out.println(found);
        }

        foundItems = queryFactory
                .selectFrom(item)
                // 가격이 6000 이하 또는 9000 이상
                // 그리고
                // 재고가 40 미만 또는 60 초과
                .where(
                        item.price.loe(6000).or(item.price.goe(9000)),
                        item.stock.lt(40).or(item.stock.gt(60))
                )
                .fetch();

        for (Item found: foundItems) {
            System.out.println(found);
        }
    }


}