package com.ecommerce.service;

import com.ecommerce.entity.Product;

import java.util.List;

public interface ProductServiceInterface {
    void createProduct(Product product);

    List<Product> showAll();

    String buyProduct(Long productId, Integer quantity);

    Product getProductById(Long id);
}