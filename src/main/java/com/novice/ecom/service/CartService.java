package com.novice.ecom.service;

import com.novice.ecom.dto.CartItemRequest;
import com.novice.ecom.dto.CartItemResponse;

import java.util.List;

public interface CartService {
    boolean addToCart(String userId, CartItemRequest cartItemRequest);

    boolean deleteFromCart(String userId, Long productId);

    List<CartItemResponse> getCart(String userId);
}
