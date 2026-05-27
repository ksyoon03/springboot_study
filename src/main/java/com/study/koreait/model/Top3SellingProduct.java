package com.study.koreait.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
    model이란?
    DB 테이블과 1:1 매핑된 entity가 아니라,
    클라이언트의 요구사항(비즈니스 로직)에 의해 만들어진
    join 결과와 1:1 매핑된 자바 클래스
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Top3SellingProduct {
    private int productId;
    private String productName;
    private int totalSoldCount; // 집계함수(count) 결과
}
