package com.study.koreait.repository.impl;

import com.study.koreait.entity.Product;
import com.study.koreait.exception.ProductException;
import com.study.koreait.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
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
}
