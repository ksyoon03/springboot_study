package com.study.koreait.controller;

import com.study.koreait.dto.ProductResDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
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
    public Map<String, Object> getProductById(@PathVariable int id){
        List<ProductResDto> dtos = List.of(
                new ProductResDto(1, "아이패드", 8500),
                new ProductResDto(2, "아이폰", 8501),
                new ProductResDto(3, "에어팟", 8502),
                new ProductResDto(4, "맥북", 8503)
        );

        Optional<ProductResDto> optDto = dtos.stream()
                .filter(s -> s.getId() == id)
                .findFirst();

        if(optDto.isEmpty()){
            return Map.of("error", "존재하지 않는 제품 id입니다.");
        }

        return Map.of("success", optDto);
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
}
