package com.novice.ecom.service.impl;

import com.novice.ecom.dto.CartItemRequest;
import com.novice.ecom.dto.CartItemResponse;
import com.novice.ecom.entity.CartItem;
import com.novice.ecom.entity.Product;
import com.novice.ecom.entity.User;
import com.novice.ecom.repo.CartItemRepository;
import com.novice.ecom.repo.ProductRepository;
import com.novice.ecom.repo.UserRepository;
import com.novice.ecom.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {

    private final ProductRepository productRepo;
    private final CartItemRepository cartItemRepo;
    private final UserRepository userRepo;

    @Override
    public boolean addToCart(String userId, CartItemRequest cartItemRequest) {

        Optional<Product> productOpt = productRepo.findById(cartItemRequest.getProductId());
        if(productOpt.isEmpty())
            return false;

        Integer requestItemQty = cartItemRequest.getQuantity();

        Product product = productOpt.get();
        if(product.getStockQuantity() < requestItemQty)
            return false;

        Optional<User> userOpt = userRepo.findById(Long.valueOf(userId));
        if(userOpt.isEmpty())
            return false;

        User user = userOpt.get();
        CartItem existingCartItem = cartItemRepo.findByUserAndProduct(user,product);

        if(existingCartItem!=null){
            //Quantity,price update
            existingCartItem.setQuantity(existingCartItem.getQuantity() + requestItemQty);
            existingCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));
        }else {
            //new cart item
            CartItem newCartItem = new CartItem();
            newCartItem.setProduct(product);
            newCartItem.setUser(user);
            newCartItem.setQuantity(requestItemQty);
            newCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(requestItemQty)));

            cartItemRepo.save(newCartItem);
        }

        return true;
    }



    @Override
    public boolean deleteFromCart(String userId, Long productId) {

        Optional<User> userOpt = userRepo.findById(Long.valueOf(userId));

        //using flatMap, we can simply have only 1 orElse() at the end, if used map we gotta use orElse() 2 times
        //userOpt.map() would produce Optional<Optional<Boolean>> because the lambda returns another Optional
        //userOpt.flatMap() unwraps that nested Optional automatically, producing Optional<Boolean>
         return userOpt.flatMap(user ->
                productRepo.findById(productId)
                        .map(product -> cartItemRepo.deleteByUserAndProduct(user, product) > 0)
//                        .orElse(false)
        ).orElse(false);
         //in the above logic, we're checking whether the userOpt is empty, if YES, return false (without even checking product exists)
        // if userOpt is not empty, check product exists, i.e., Optional<Product> is empty, if YES return false
        //if both are not empty, then deletion logic works, and return true

    }

    @Override
    public List<CartItemResponse> getCart(String userId) {
        Optional<User> userOpt = userRepo.findById(Long.valueOf(userId));


        return userOpt.map(user -> cartItemRepo.findAllByUser(user).stream()
                            .map(this::mapToCartItemResponse)
                            .toList())
                .orElse(List.of());
    }

    private CartItemResponse mapToCartItemResponse(CartItem cartItem){
        CartItemResponse cartItemResponse = new CartItemResponse();
        cartItemResponse.setId(cartItem.getId());
        cartItemResponse.setPrice(cartItem.getPrice());
        cartItemResponse.setProductName(cartItem.getProduct().getName());
        cartItemResponse.setQuantity(cartItem.getQuantity());
        return cartItemResponse;
    }
}
