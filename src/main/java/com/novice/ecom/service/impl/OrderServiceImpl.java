package com.novice.ecom.service.impl;

import com.novice.ecom.dto.OrderItemDto;
import com.novice.ecom.dto.OrderResponse;
import com.novice.ecom.entity.CartItem;
import com.novice.ecom.entity.Order;
import com.novice.ecom.entity.OrderItem;
import com.novice.ecom.entity.User;
import com.novice.ecom.enums.OrderStatus;
import com.novice.ecom.exceptions.BadRequestException;
import com.novice.ecom.exceptions.ResourceNotFoundException;
import com.novice.ecom.repo.CartItemRepository;
import com.novice.ecom.repo.OrderRepository;
import com.novice.ecom.repo.UserRepository;
import com.novice.ecom.service.CartService;
import com.novice.ecom.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final UserRepository userRepo;
    private final CartItemRepository cartItemRepo;
    private final OrderRepository orderRepo;
    private final CartService cartService;

    @Override
    public OrderResponse createOrder(String userId) {

        User user = userRepo.findById(Long.valueOf(userId))
                .orElseThrow( ()-> new ResourceNotFoundException("User does not exist"));

        List<CartItem> cartItems = cartItemRepo.findAllByUser(user);
        if (cartItems.isEmpty())
            throw new BadRequestException("cart is empty, add some items first");

        BigDecimal totalPrice = cartItems.stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO, (itemTotal,subTotal) -> itemTotal.add(subTotal));

        Order order = new Order();
        order.setUser(user);
        order.setOrderStatus(OrderStatus.CONFIRMED);
        order.setTotalAmount(totalPrice);

        //order items from cart items
        List<OrderItem> orderItems = cartItems.stream()
                .map(item -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrder(order);
                    orderItem.setQuantity(item.getQuantity());
                    orderItem.setPrice(item.getPrice());
                    orderItem.setProduct(item.getProduct());
                    return orderItem;
                }).toList();

        order.setItems(orderItems);
        Order savedOrder = orderRepo.save(order);

        cartService.clearCart(userId);

        return mapToOrderResponse(savedOrder);
    }

    //mapping methods
    private OrderResponse mapToOrderResponse(Order order){
        return new OrderResponse(
                order.getId(),
                order.getTotalAmount(),
                order.getOrderStatus(),
                order.getItems().stream()
                        .map(this::mapToOrderItemDto)
                        .toList(),
                order.getCreatedAt()
        );
    }

    private OrderItemDto mapToOrderItemDto(OrderItem orderItem){
        return new OrderItemDto(
                orderItem.getId(),
                orderItem.getProduct().getId(),
                orderItem.getQuantity(),
                orderItem.getPrice(),
                orderItem.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())));
    }

}
