package com.ecommerce.repository;

import com.ecommerce.entity.Product;

import java.util.List;

public interface ProductRepository {
    void save(Product product);

    Product findById(Long id);

    List<Product> findAll();

    int deductStock(Long id, Integer amount);
}
