package com.study.koreait.controller;

import com.study.koreait.dto.AddPostReqDto;
import com.study.koreait.dto.AddProductReqDto;
import com.study.koreait.dto.ProductResDto;
import com.study.koreait.exception.ProductException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/study2")
public class StudyController2 {

    @GetMapping("/add")
    public Integer add(int a, int b){
        log.info("합 계산 완료");
        log.info("합: {}", a+b);
        return a + b;
    }

    @GetMapping("/avg")
    public Double avg(int a, int b, int c){
        log.info("평균 계산 완료");
        log.info("평균: {}", (a + b + c) / 3.0);
        return (a + b + c) / 3.0;
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<?> getProductById(@PathVariable int id){
        List<ProductResDto> dtos = List.of(
                new ProductResDto(1, "아이패드", 8500),
                new ProductResDto(2, "아이폰", 8501),
                new ProductResDto(3, "에어팟", 8502),
                new ProductResDto(4, "맥북", 8503)
        );

        Optional<ProductResDto> optDto = dtos.stream()
                .filter(s -> s.getId() == id)
                .findFirst();

        // Optional.orElseThrow() -
        // 커스텀 에러를 던져줘야 한다.
        ProductResDto dto = optDto.orElseThrow(()
                -> new ProductException("해당 id는 존재하지 않습니다.", HttpStatus.NOT_FOUND));

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/product/range")
    public Map<String, Object> getProductByPriceRange(@RequestParam int minPrice, @RequestParam int maxPrice){
        List<ProductResDto> dtos = List.of(
                new ProductResDto(1, "아이패드", 8500),
                new ProductResDto(2, "아이폰", 8501),
                new ProductResDto(3, "에어팟", 8502),
                new ProductResDto(4, "맥북", 8503)
        );

        List<ProductResDto> datas = dtos.stream()
                .filter(p -> p.getPrice() >= minPrice && p.getId() <= maxPrice)
                .toList();

        if(datas.isEmpty()){
            return Map.of("error", "해당 가격의 제품은 없습니다.");
        }

        return Map.of("success", datas);
    }

    // post(만들겠다) + /post => 게시글을 등록하겠다
    // post 요청 + /posts =? 게시글 여러개를 등록하겠다
    // (요청 메서드 + 요청 URL)은 중복되면 안됨
    @PostMapping("/post")
    public ResponseEntity<?> makePost(@RequestBody @Valid AddPostReqDto dto){
        log.info("들어온 데이터: {}", dto);

        return ResponseEntity   // header와 body로 나뉨
                .status(HttpStatus.CREATED)
                .body("성공");
    }

    // PUT: 수정 - 전체 덮어쓰기
    // PATCH: 수정 - 부분 수정

    // DELETE는 body가 있으나, path로 지정
    @DeleteMapping("/post/{id}")
    public ResponseEntity<?> deletePost(@PathVariable int id){
        return ResponseEntity.ok("성공");
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestBody @Valid AddProductReqDto dto){
        log.info("들어온 데이터: {}", dto);
        return ResponseEntity.ok("상품 등록 완료");
    }
}
