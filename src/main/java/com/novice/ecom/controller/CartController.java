package com.novice.ecom.controller;

import com.novice.ecom.dto.CartItemRequest;
import com.novice.ecom.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<String> addToCart(@RequestHeader("X-User-ID") String userId,
                                          @RequestBody CartItemRequest cartItemRequest){

        if(!cartService.addToCart(userId,cartItemRequest))
            return ResponseEntity.badRequest()
                    .body("Product out of stock");

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
