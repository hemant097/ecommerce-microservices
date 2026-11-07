package com.novice.ecom.service;

import com.novice.ecom.dto.OrderResponse;

public interface OrderService {
    OrderResponse createOrder(String userId);
}
