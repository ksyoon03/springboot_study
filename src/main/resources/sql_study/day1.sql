# day1

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

INSERT INTO product(product_id, product_name, price)
VALUES(
	4,
    "카푸치노",
    5000
), (
	5,
    "아메리카노",
    4500
), (
	6,
    "카페라떼",
    5200
);


CREATE TABLE post(
	post_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(25) NOT NULL,
    content TEXT,
    create_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO post(title, content)
VALUES(
	"부산 여행 후기",
    "광안리 야경이 정말 멋졌다."
), (
	"MySQL 공부중",
    NULL
), (
	"영화 감상",
    "어제 본 영화는 평점에 비해 별로였다."
);

SELECT * FROM post;

INSERT INTO post(post_id, title)
VALUES (1, '테스트');	-- 자바에서는 SQL Exception 형탤 메세지가 전파됨.

-- "좋아요" 컬럼을 추가해야 한다면?
ALTER TABLE `webstudy`.`post` 
ADD COLUMN `like_count` INT NOT NULL DEFAULT 0 AFTER `create_at`;
