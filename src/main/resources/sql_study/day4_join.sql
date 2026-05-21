# day4_join
/*
	정규화
    1. 갱신 이상 - 상품 가격 업데이트 시 모든 행 업데이트
    2. 삽입 이상 - 주문되지 않은 상품은 기록조차 되지 못한다
    3. 삭제 이상 - 주문을 지웠더니, 손님 정보가 아예 사라질 수 있음
    
    정규화 단계
    1정규화 - 한 셀에는 값 하나만
    2정규화 - pk가 여러 컬럼일 때, 부분적으로만 결정되는 컬럼이 없도록
    3정규화 - 다른 컬럼을 거쳐서 결정되는 컬럼이 없도록
    
    -> 결국에는 정보 취합을 해야한다(join)
    -> 취합을 할 때 연결고리(외래키-fk)가 필요함
*/

# innerjoin
-- 양쪽 테이블에 모두 있는 데이터를 합쳐준다 (NULL은 제외)
SELECT *
FROM orders o INNER JOIN order_details od ON o.order_id	= od.order_id;

-- product_id만 있고, 상품명 X
SELECT	o.customer_name,
		o.ordered_at,
        p.product_name,
        p.price,
        od.quantity
FROM orders o
	INNER JOIN order_details od ON o.order_id = od.order_id
	INNER JOIN product p ON od.product_id = p.product_id;

-- post와 comments INNER JOIN 하기
-- 결과 컬럼: post_id, title, author, body
-- post_id 오름차순 정렬, 같다면 comment_id 오름차순
SELECT p.post_id, p.title, co.author, co.body
FROM post p INNER JOIN comments co ON p.post_id = co.post_id
ORDER BY p.post_id ASC,
		 co.comment_id ASC;
-- NULL을 제외하고 보여줌
-- 6, 8, 9, 10, 11, 12는 댓글이 없기 때문에 표현이 안됨

SELECT * FROM post;
    





