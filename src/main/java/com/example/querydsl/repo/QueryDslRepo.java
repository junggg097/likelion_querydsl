package com.example.querydsl.repo;

import com.example.querydsl.entity.Item;
import com.example.querydsl.entity.QItem;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class QueryDslRepo {
    /**
    * === SELECT 문 ( Statement ) ===
    * SELECT i                -- SELECT 절 ( Clause )
    * FROM Item i             -- FROM 절 ( Clause )
    * WHERE i.name = :name    -- WHERE 절 ( Clause )
     **/
    private final JPAQueryFactory queryFactory;
    private final ItemRepository itemRepository;

    /*
    public QueryDslRepo(
            EntityManager entityManager
    ) {
        queryFactory = new JPAQueryFactory(entityManager);
    }
    */

    public void helloQuerydsl() {
        itemRepository.save(Item.builder()
                .name("new item")
                .price(1000)
                .stock(1000)
                .build());


        // QItem 은 엔티티 그리고 엔티티가 가질 수 있는 속성을 나타낸다.
        /*
            SELECT i
            FROM Item i
            WHERE i.name = :name
         */
        QItem qItem = new QItem("item");
        List<Item> items = queryFactory
                // SELECT 절 추가
                .select(qItem)
                // FROM 절 추가
                .from(qItem)
                // WHERE 절 추가
                .where(qItem.name.eq("new item"))
                // 결과 조회
                .fetch();

        for (Item item: items) {
            log.info("{}:{} ({})", item.getName(), item.getPrice(), item.getStock());
        }
    }
}
