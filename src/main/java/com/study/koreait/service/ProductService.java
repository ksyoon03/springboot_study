package com.study.koreait.service;

import com.study.koreait.dto.FindProductResDto;
import com.study.koreait.entity.Product;
import com.study.koreait.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    // 인터페이스 타입을 필드로 가진다
    // -> 의존성이 느슨하기 때문에 변경이 쉽다
    private final ProductRepository repository;

    // 전체 상품 리스트를 리턴 - dto
    public List<FindProductResDto> getProductList(){
        return repository.findAllProducts()
                .stream()
                // 매개변수가 호출만 될 때 or 다음 메서드에 전달만 될 때
                // 메서드 참조라는 람다 생략식을 작성할 수 있음
                .map(Product::toFindProductResDto)
                .toList();
    }

    // 특정 상품을 리턴 - dto
    // 메서드마다 dto를 작성해줘야함
    public FindProductResDto getProductById(int id){
        return repository.findProductById(id).toFindProductResDto();
    }
}
