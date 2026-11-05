package com.novice.ecom.controller;

import com.novice.ecom.dto.ProductRequest;
import com.novice.ecom.dto.ProductResponse;
import com.novice.ecom.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest){

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productService.createProduct(productRequest));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> searchProducts(@RequestParam(value = "keyword") String name){
        return ResponseEntity.ok(productService.searchProducts(name));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id,
                                                         @RequestBody ProductRequest updatedProductReq){
        return productService.updateProduct(id,updatedProductReq)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id,
                                                         @RequestBody ProductRequest updatedProductReq){
        boolean deleted = productService.deleteProduct(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

}