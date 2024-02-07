package com.example.querydsl.repo;

import com.example.querydsl.dto.ItemSearchParams;
import com.example.querydsl.entity.Item;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.example.querydsl.entity.QItem.item;

@Slf4j
@RequiredArgsConstructor
public class ItemQuerydslRepoImpl implements ItemQuerydslRepo {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Item> searchDynamic(ItemSearchParams searchParams) {
        // TODO 동적 쿼리로 결과 반환하기
        log.info(searchParams.toString());
        return queryFactory.selectFrom(item).fetch();
    }

    @Override
    public Page<Item> searchDynamic(ItemSearchParams searchParams, Pageable pageable) {
        log.info(searchParams.toString());
        // TODO 동적 쿼리로 결과 반환하기
        // Page를 만드는데 필요한 정보
        // 1. (페이지 처리 된) 실제 데이터 (offset, limit)
        List<Item> content = queryFactory
                .selectFrom(item)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        // 2. Pageable에 대한 데이터 (몇번째 페이지, 페이지 당 내용)
        //      -> 인자로 주어진다.

        // 3+@. 총 갯수를 반환할 수 있는 방법
        JPAQuery<Long> countQuery = queryFactory
                .select(item.count())
                .from(item);
        // PageableExecutionUtils.getPage()
        // 1. 첫번째 페이지
        // 2. (페이지 당 갯수를 채우지 못한) 마지막 페이지
        // 의 경우에는 Count 쿼리를 실행하지 않는다.
        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);


        /*// 3. 총 갯수 (총 페이지를 위해서 필요한 정보)
        Long count = queryFactory
                .select(item.count())
                .from(item)
                .fetchOne();
        // PageImpl로 반환
        return new PageImpl<>(content, pageable, count);*/
    }
}