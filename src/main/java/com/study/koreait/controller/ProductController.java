package com.study.koreait.controller;

import com.study.koreait.dto.AddProductReqDto;
import com.study.koreait.dto.ModifyProductReqDto;
import com.study.koreait.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Modifier;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService service;

    @GetMapping("/all")
    public ResponseEntity<?> getProducts(){
        return ResponseEntity.ok(service.getProductList());
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getProduct(@PathVariable int id){
        return ResponseEntity.ok(service.getProductById(id));
    }

    @PostMapping("/add")
    public ResponseEntity<?> postProduct(@RequestBody AddProductReqDto dto){
        int successCount = service.addProduct(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(successCount + "건 등록 성공");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable int id){
        int successCount = service.removeProduct(id);
        return ResponseEntity.ok(successCount + "건 삭제 완료");
    }

    // RESTful -> id는 path로 지정,
    // 구체적인 내용은 body로
    @PutMapping("/{id}")
    public ResponseEntity<?> putProduct(@PathVariable int id, @RequestBody @Valid ModifyProductReqDto dto) {
        int successCount = service.modifyProduct(id, dto);
        return ResponseEntity.ok(successCount + "건 수정 완료");
    }
    
    @GetMapping("/search")
    public ResponseEntity<?> getProductByName(@RequestParam String name){
        return ResponseEntity.ok(service.getSearchProducts(name));
    }
}
