package com.study.koreait.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Comments {
    private int commentId;
    private int postId;
    private String author;
    private String body;
    private LocalDateTime createAt;

    /*
        데이터베이스는 외래키를 통해 테이블끼리 결합
        자바에서는 참조를 통해 객체끼리 결합(필드 선언)

        1:N 관계 - 외래키를 가진 쪽이 N
        1:1 관계 - 양방향 참조만 조심

        N:M 관계 - 중간 테이블을 만들어서 1:N <> 1:M 관계를 형성
    */
}
