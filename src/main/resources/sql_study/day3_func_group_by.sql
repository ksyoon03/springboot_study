-- day3_func_group_by

USE webstudy;

# 집계함수
SELECT	COUNT(*) AS 개수,		-- COUNT(컬럼명 or *)
		SUM(price) AS 합계,	-- SUM(컬럼명)
		AVG(price) AS 평균,	-- AVG(컬럼명)
        MIN(price) AS 최소치, -- MIN(컬럼명)
        MAX(price) AS 최대치	-- MAX(컬럼명)
FROM product;
    
-- NULL을 허용하는 컬럼은 COUNT() 시 주의해야함!
-- COUNT(*)은 NULL도 카운팅함
-- COUNT(컬럼)은 NULL은 카운팅하지 않음

# ROUND() - 소수를 다룰 때
SELECT	ROUND(AVG(price)) AS 정수	,	-- 정수로 반올림
		ROUND(AVG(price), 1) AS 소수점한자리,	-- 두번째 매개변수가 양수면 소수자리 표현
        ROUND(AVG(price), 2) AS 소수점두자리,
        ROUND(AVG(price), -3) AS 백원단위 -- 두번째 매개변수가 음수면 정수자리 반올림
FROM product;

# IFNULL(컬럼, 대체할 값) : NULL이라면 대체할 값으로 치환
SELECT	IFNULL(content, '비어있음') AS content
FROM post;

# 날짜 함수
SELECT NOW(); -- 현재 시간
INSERT INTO	post(title, content, create_at)
VALUES(
	'SK 하이닉스 다시 떡상',
    NULL,
    NOW()
);

/*
DATE_FORMAT(연도, 포맷 표현) : 원하는 모양으로 출력하고 싶을 때
%Y 4자리 연도, 	%y 2자리 연도
%m 월(01 ~ 12)	%c 월(1 ~ 12)
%d 일(01 ~ 31)	%e 일(1 ~ 31)
%H 시(24시 기준) %i 분 %s 초
*/

SELECT	product_name,
		DATE_FORMAT(create_at, '%Y년 %m월 %d일') AS create_at
FROM product;

# CONCAT() : 문자열 이어 붙이기
SELECT	CONCAT(product_name, ' : ', price, '원') AS 메뉴판
FROM product;

# CASE-WHEN : if-else if-else의 sql 버전
-- 위에서 아래로 평가되며, 처음 true가 될 곳에서 실행하고 탈출
SELECT 	product_name,
		price,
        CASE -- 조건문 시작
			WHEN price < 2500 then '저렴' -- 조건
            WHEN price < 5000 then '보통' -- 조건
            ELSE '비쌈' -- 그 외
		END AS 가격대 -- 조건문 종료
FROM product;

-- 1) post 테이블에서 가장 오래된 글의 작성 시간과 가장 최근 글의 작성 시간 출력하기
SELECT	MIN(create_at) AS 가장오래,
		MAX(create_at) AS 가장최근
FROM post;

-- 2) post 테이블에서 모든 글을 '제목 - 본문'으로 된 하나의 값으로 표현하기
-- NULL일 경우, '(본문 없음)'으로 대체하기
SELECT	CONCAT(title, ' - ', IFNULL(content, '(본문없음)')) AS 표현식
FROM post;

-- 3) post 테이블에서 제목, 작성시간, 시간대(오전(0~12), 오후(13~18), 밤(19~24))으로 표현하기
SELECT	title,
		create_at,
        CASE
			WHEN DATE_FORMAT(create_at, '%H') <= 12 THEN '오전'
            WHEN DATE_FORMAT(create_at, '%H') <= 18 THEN '오후'
            ELSE '밤'
		END AS 시간대
FROM post
ORDER BY create_at asc;

# GROUP BY
-- 중복값을 하나의 값으로 압축
-- 집계함수와 함께 쓰임
SELECT	price,
		COUNT(*) AS 상품수 -- 그룹화 한 것
FROM product
GROUP BY price;

-- 월별 등록 상품 개수
SELECT	DATE_FORMAT(create_at, '%Y-%m') AS 월,
		COUNT(*) AS 상품_갯수
FROM product
GROUP BY DATE_FORMAT(create_at, '%Y-%m')
ORDER BY 월;	-- ORDER BY는 SELECT 보다 뒤쪽 순서

# HAVING - 그룹 결과에 다시 조건을 걸기 위해 존재함
-- WHERE -> 그룹화 전, 원래 행에 조건
-- HAVING -> 그룹화 후, 묶음 결과에 조건
SELECT	price,
		COUNT(*) AS 상품수 -- 그룹화 한 것
FROM product
GROUP BY price
HAVING COUNT(*) >= 2;
-- COUNT(*)는 그룹화 후에 나오는 값이라 WHERE에선 쓸 수 없음

/*
	1. FROM - 테이블 & 조인 수행
	2. WHERE - 행 필터링
	3. GROUP BY - 그룹화
	4. HAVING - 그룹 필터링
	5. SELECT - 컬럼 필터링, AS(별칭)가 메모리에 생성됨
	6. ORDER BY - 정렬
	7. LIMIT OFFSET - 몇 개?
*/

-- product 테이블에서 가격대로 가격대별 가격 합계 및 평균가
-- 정렬: 평균가 비싼 순
-- 가격대 분류: 저렴( < 2500), 보통( < 4000), 비쌈(그외)
SELECT	
		CASE
			WHEN price < 2500 THEN '저렴'
            WHEN price < 4000 THEN '보통'
            ELSE '비쌈'
        END AS 가격대,
        COUNT(*) AS 상품수
FROM product
GROUP BY
		CASE
			WHEN(price < 2500) THEN '저렴'
            WHEN(price < 4000) THEN '보통'
            ELSE '비쌈'
        END
HAVING COUNT(*) >= 2
ORDER BY 상품수 DESC;




