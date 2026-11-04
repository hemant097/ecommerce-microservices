package com.novice.ecom.service.impl;

import com.novice.ecom.dto.ProductRequest;
import com.novice.ecom.dto.ProductResponse;
import com.novice.ecom.entity.Product;
import com.novice.ecom.repo.ProductRepository;
import com.novice.ecom.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepo;

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {

        Product product = new Product();
        updateProductFromRequest(product, productRequest);
        Product savedProduct = productRepo.save(product);
        return mapToProductResponse(savedProduct);
    }

    private void updateProductFromRequest(Product product, ProductRequest request){
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setCategory(request.getCategory());
        product.setDescription(request.getDescription());
        product.setImageUrl(request.getImageUrl());
        product.setStockQuantity(request.getStockQuantity());

    }

    private ProductResponse mapToProductResponse(Product product){
         ProductResponse response = new ProductResponse();

         response.setId(product.getId());
         response.setName(product.getName());
         response.setPrice(product.getPrice());
         response.setCategory(product.getCategory());
         response.setActive(product.getActive());
         response.setImageUrl(product.getImageUrl());
         response.setDescription(product.getDescription());
         response.setStockQuantity(product.getStockQuantity());

         return response;
    }
}
