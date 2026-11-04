package com.novice.ecom.service;

import com.novice.ecom.dto.ProductRequest;
import com.novice.ecom.dto.ProductResponse;

public interface ProductService {

    ProductResponse createProduct(ProductRequest productRequest);
}
