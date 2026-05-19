package com.study.koreait.repository.impl;

import com.study.koreait.entity.Product;
import com.study.koreait.exception.ProductException;
import com.study.koreait.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class ProductRepoImpl implements ProductRepository {

    // DB 대용 데이터
    private final List<Product> products = new ArrayList<>(
            Arrays.asList(
                    Product.builder().productId(1).productName("노트북").price(190).build(),
                    Product.builder().productId(2).productName("휴대폰").price(140).build(),
                    Product.builder().productId(3).productName("헤드셋").price(30).build(),
                    Product.builder().productId(4).productName("마우스").price(8).build()

                    )
    );

    // 전체 데이터 조회 -> db를 풀스캔하는 메서드는 없어야함.
    // 페이지네이션으로 조회하게끔 변경해야함.
    @Override
    public List<Product> findAllProducts() {
        return products;
    }


    @Override
    public Product findProductById(int id) {

        // db 쿼리가 되어야함
        Optional<Product> optProduct = products.stream()
                .filter(p -> p.getProductId() == id)
                .findFirst();

        if(optProduct.isEmpty()){
            throw new ProductException("해당 id의 상품은 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }

        return optProduct.get(); // null이 아닐 때만 리턴
    }

    @Override
    public int insertProduct(Product product) {
        // 차후에는 쿼리로 대체
        // id 최댓값을 찾아야함 - AUTO INCREMENT로 대체
        int maxId = 0;
        for(Product p : products){
            int productId = p.getProductId();
            if(productId > maxId){
                maxId = productId;
            }
        }

        Product newProduct = Product.builder()
                .productId(maxId + 1)
                .productName(product.getProductName())
                .price(product.getPrice())
                .build();

        products.add(newProduct);

        return 1; // db에서도 단건 추가에 대해서 1을 리턴함
    }

    @Override
    public int deleteProductByID(int id) {
        // 매개변수로 들어온 id가 유효한가?
        Optional<Product> optProduct = products.stream()
                .filter(p -> p.getProductId() == id)
                .findFirst();

        // 조건문 방식
        /*
        if(optProduct.isEmpty()) {
            throw new ProductException("해당 id의 상품은 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }
        Product product1 = optProduct.get();
         */

        // 예외처리 방식
        Product product2 = optProduct.orElseThrow(() -> new ProductException("해당 id의 상품은 존재하지 않습니다.", HttpStatus.NOT_FOUND));

        products.remove(product2);
        log.info("상품 삭제완료: {}", product2);

        return 1;   // db에서도 단건 삭제의 경우 1 리턴
    }

    // PATCH -> 부분 수정
    // PUT -> 전체 수정

    @Override
    public int updateProductById(Product product) {
        // id가 실제 있는 id인지 검사 (유효성)
        int productId = product.getProductId();
        Optional<Product> optProduct = products.stream()
                .filter(p -> p.getProductId() == productId)
                .findFirst();

        if(optProduct.isEmpty()){
            throw new ProductException("유효하지 않은 접근입니다.", HttpStatus.BAD_REQUEST);
        }

        // 리스트 업데이트
        // 리스트.set(index, 저장할 데이터); -> 덮어쓰기

        // filter로 id가 동일한 객체를 찾아서 index를 추출
        int index = products.indexOf(optProduct.get());
        // 해당 index에 매개변수로 들어온 객체로 덮어쓰기
        products.set(index, product);

        return 1;
    }

    @Override
    public List<Product> searchProductByName(String name) {
        // 선택) 예외를 던지거나 or 빈리스트를 리턴하거나
        List<Product> returnData = products.stream()
                .filter(p -> p.getProductName().contains(name))
                .toList();


        return returnData;
    }
}
