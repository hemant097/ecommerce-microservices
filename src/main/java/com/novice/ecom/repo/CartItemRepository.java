package com.novice.ecom.repo;

import com.novice.ecom.entity.CartItem;
import com.novice.ecom.entity.Product;
import com.novice.ecom.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem findByUserAndProduct(User user, Product product);

    int deleteByUserAndProduct(User user, Product product);

    List<CartItem> findAllByUser(User user);

    void deleteByUser(User user);
}
