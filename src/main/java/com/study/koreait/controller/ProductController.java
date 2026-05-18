package com.study.koreait.controller;

import com.study.koreait.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
