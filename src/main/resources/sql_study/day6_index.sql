# 인덱스(index)
-- 풀스캔: 처음부터 끝까지 모두 스캔 (데이터가 쌓이면 느려짐)
-- 인덱스는 데이터의 색인을 만들어주는 기능
-- B+tree 구조를 가지고 있어서 어떤 값을 찾을 때 좀 더 빠르게 찾을 수 있음
-- primary key 컬럼은 자동으로 index가 만들어진다.
-- unique 제약도 마찬가지로 자동으로 index가 만들어진다.

-- CREATE INDEX 인덱스명 ON 테이블명(컬럼명)
CREATE INDEX index_comments_post_id ON comments (post_id);

-- 만들어진 인덱스 목록 조회
SHOW INDEX FROM comments;

-- 인덱스 삭제
-- DROP INDEX 인덱스명 ON 테이블명
DROP INDEX index_comments_post_id ON comments;

# 일반적으로 index를 어디에 걸어야 할까?
-- 1. WHERE절에 자주 쓰이는 컬럼
-- 2. JOIN할 때 ON절에 자주 쓰이는 컬럼
-- 3. ORDER BY에 자주 쓰이는 컬럼

# 인덱스를 못타는 경우
-- 1. LIKE 연산 시 '%라떼%' -> 앞쪽에 %가 있으면 인덱스가 무효화됨
-- '아이스%' 식으로 작성하는 것이 인덱스를 활용하기 좋음
-- 2. 컬럼에 함수를 적용하면 인덱스가 무효화됨
-- year(create_at) = 2025 -> 2025년에 해당하는 모든 컬럼
-- create_at >= '2025-01-01' AND create_at < '2026-01-01'

# 실행계획 조회해보기
-- 쿼리 앞에 EXPLAIN을 붙히면 실행계획 조회 가능

EXPLAIN SELECT * FROM comments;
/*
	실행계획의 type
    ALL -> 풀스캔
    index -> 인덱스 스캔
    ref(eq_ref) -> 인덱스를 비교 연산해서 사용
    const -> 상수
*/

EXPLAIN SELECT * FROM comments WHERE post_id = 3;





