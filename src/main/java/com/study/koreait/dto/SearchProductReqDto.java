package com.study.koreait.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SearchProductReqDto {
    // 세 필드 모두 있을 수 도 있고, 없을 수도 있음
    // 없을 때는 null로 통일하기 위해 Wrapper를 사용
    private String nameKeyword;
    private Integer minPrice;
    private Integer maxPrice;
}