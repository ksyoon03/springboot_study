# day2_select

-- 전체 조회
SELECT * -- 컬럼 이름, *: 모든 컬럼
FROM product; -- 테이블 or 가상 테이블

SELECT product_name AS 제품명, price AS 가격	-- as(별칭) 결과 컬럼 이름을 바꿀 수 있음
FROM product;
    
    
-- WHERE절 : if문
SELECT *
FROM product
WHERE product_id = 4;
    
SELECT * FROM product WHERE product_id <> 4;	-- !와 같은 동작
SELECT * FROM product WHERE price >= 5000;
-- between 연산: ~이상, ~이하
SELECT * FROM product WHERE price BETWEEN 3000 and 4900;
SELECT * FROM product WHERE create_at BETWEEN '2026-05-19 00:00:00' and '2026-05-21 00:00:00';

-- in 연산 "이 중에 하나라도 맞으면" (OR의 단축형)
SELECT * FROM product WHERE product_name IN ("카푸치노, 아메리카노");

-- LIKE 연산 "패턴매칭"
SELECT * FROM product WHERE product_name LIKE '%라떼%'; -- '라떼'가 들어가는 모든 것
SELECT * FROM product WHERE product_name LIKE '카%'; -- '카'로 시작하는 모든 것
-- b-tree 구조 때문에 ~로 시작하는 검색이 빠르다
SELECT * FROM product WHERE product_name LIKE '__쿠키'; -- 정확히 2글자 + '쿠키'인 것

-- NULL을 다루는 연산
-- NULL은 = or != 연산을 사용할 수 없음
SELECT * FROM post WHERE content IS NULL;
SELECT * FROM post WHERE content IS NOT NULL;

-- ORDER BY : 정렬
SELECT * FROM product ORDER BY product_name ASC; -- 오름차순 (기본)
SELECT * FROM product ORDER BY product_name DESC; -- 내림차순

-- 동률일 때, 추가 기준 생성 가능
SELECT 
	*
FROM 
	product 
ORDER BY 
	price DESC,
    product_name ASC;
    
-- LIMIT / OFFSET
-- LIMIT n: 위에서부터 n개만 보여줌
-- LIMIT n OFFSET m: m개를 건너뛰고, 위에서 n개만 보여줌
-- 페이지네이션에서 한 번에 n개의 게시글을 보여줄 때
-- LIMIT n OFFSET n * (page - 1)
SELECT * FROM product ORDER BY price DESC LIMIT 3;
SELECT
	*
FROM
	post	
ORDER BY
	create_at DESC
LIMIT 2 OFFSET 1; -- 최신순, 2개씩 볼 때 2페이지 게시글들

-- DISTINCT : 중복되는 row 제거
SELECT DISTINCT
	price AS 가격
FROM 
	product
ORDER BY
	PRICE ASC;
    
INSERT INTO post (title, content, create_at) VALUES
  ('첫 글입니다',           '블로그를 새로 시작했습니다. 잘 부탁드려요!',                  '2025-09-01 10:00:00'),
  ('오늘의 일기',           '오늘은 비가 와서 하루 종일 집에 있었다. 라면을 끓여 먹었다.', '2025-09-05 21:30:00'),
  ('강남 맛집 추천 3곳',     '1. A 식당  2. B 카페  3. C 떡볶이. 모두 가성비 좋아요.',   '2025-09-12 18:00:00'),
  ('책 리뷰: 모순',          '양귀자 작가의 모순을 읽었다. 인생에 대해 다시 생각하게 됨.', '2025-09-20 22:00:00'),
  ('부산 여행 후기',         '광안리 야경이 정말 멋졌다. 회도 신선했고.',                 '2025-10-01 12:00:00'),
  ('MySQL 공부중',           NULL,                                                       '2025-10-08 09:00:00'),
  ('영화 감상',              '어제 본 영화는 평점에 비해 별로였다. 너무 길었음.',          '2025-10-15 23:00:00'),
  ('짧은 메모',              '내일까지 보고서 마무리할 것.',                              '2025-10-25 08:00:00');

-- 1) 제목에 '리뷰'가 포함된 게시글들을 조회하기
SELECT * 
FROM post 
WHERE title 
LIKE '%리뷰%';

-- 2) content가 null이 아닌 게시들들 중 오래된 순으로 3개만 조회하기
SELECT *
FROM post
WHERE content IS NOT NULL
ORDER BY create_at ASC;

-- 3) post에서 가장 최신순으로 제목만 3개 조회하고, 컬럼 이름을 '제목'으로 설정하기
SELECT title AS '제목'
FROM post
ORDER BY create_at DESC
LIMIT 3;

/*

SELECT 실행 순서
1) FROM - 어느 테이블?
2) WHERE - 어떤 행만?
3) SELECT - 어떤 컬럼만?
4) ORDER BY - 정렬
5) LIMIT OFFSET - 몇 개?

*/




