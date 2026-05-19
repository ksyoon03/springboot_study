-- database 생성
CREATE DATABASE webStudy;

-- database 생성
USE webStudy;

/*

데이터베이스의 자료형
1. INT -> int / INTEGER
2. VARCHAR(n) -> String : 길이 n의 문자열
3. TEXT -> String : 길이 무제한 문자열
4. DATETIME -> LocalDateTime : 날짜 + 시간 ex) '2026-05-19 21:31:15'
5. DECIMAL(p, s) -> BigDecimal : 소수점 고정 ex) DECIMAL(30.123123, 2) -> 30.12

*/

-- 테이블(자바의 클래스와 1:1 대응) 생성
CREATE TABLE product(
	product_id INT NOT NULL AUTO_INCREMENT,
    product_name VARCHAR(50) NOT NULL,
    price INT NOT NULL,
    create_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(product_id)
);

/*

제약사항 & 기타
1. NOT NULL -> 값을 비워둘 수 없음
2. PRIMARY KEY -> 해당 row를 다른 모든 row와 구분 짓는 컬럼 (해당 테이블의 대표값)
3. DEFAULT -> insert 할 때 비워두면, 기본값으로 DEFAULT 뒤의 값을 넣어줌

*/

SELECT * FROM product;

