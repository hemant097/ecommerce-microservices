package com.novice.ecom.service;

import com.novice.ecom.dto.ProductRequest;
import com.novice.ecom.dto.ProductResponse;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    ProductResponse createProduct(ProductRequest productRequest);

    List<ProductResponse> getAllProducts();

    Optional<ProductResponse> updateProduct(Long id, ProductRequest updatedProductReq);

    boolean deleteProduct(Long id);

    List<ProductResponse> searchProducts(String keyword);
}
