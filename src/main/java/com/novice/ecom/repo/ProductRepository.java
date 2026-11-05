package com.novice.ecom.repo;

import com.novice.ecom.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findByActiveTrue();

    @Query("""
            SELECT p from products p where
                        p.active = true and p.stockQuantity > 0 and
                        lower(p.name) LIKE lower(concat('%',:product_name,'%') )
    """)
    List<Product> findByName(@Param(value = "product_name") String keyword);
}
