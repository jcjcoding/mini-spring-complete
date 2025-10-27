package com.ecommerce.repository;

import com.ecommerce.entity.Product;
import com.ecommerce.ioc.Component;

@Component("redisRepository")
public class RedisProductRepository implements ProductRepository {
    @Override
    public void save(Product product) {
        System.out.println("商品缓存至Redis"+product);
    }
}
