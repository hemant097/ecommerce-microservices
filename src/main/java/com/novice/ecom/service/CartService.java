package com.novice.ecom.service;

import com.novice.ecom.dto.CartItemRequest;

public interface CartService {
    boolean addToCart(String userId, CartItemRequest cartItemRequest);
}
