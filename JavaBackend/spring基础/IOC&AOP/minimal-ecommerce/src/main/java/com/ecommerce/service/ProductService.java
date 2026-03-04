package com.ecommerce.service;

import com.ecommerce.entity.Product;
import com.ecommerce.ioc.*;
import com.ecommerce.repository.MysqlProductRepository;
import com.ecommerce.repository.ProductRepository;

import java.util.HashMap;
import java.util.Map;

@Component
public class ProductService implements ProductServiceInterface{
    @Autowired
    @Qualifier("mysqlRepo")
    private ProductRepository ProductRepository;

    //模拟商品缓存
    private Map<Long,Product> productCache;

    @PostConstruct
    public void initCache() {
        this.productCache = new HashMap<Long, Product>();
        System.out.println("[ProductService初始化] 商品缓存加载完成，容量：" + productCache.size());
    }
    @PreDestroy
    public void clearCache() {
        productCache.clear();
        System.out.println("[ProductService销毁] 商品缓存已清理");
    }

    public void setProductRepository(MysqlProductRepository ProductRepository) {
        this.ProductRepository = this.ProductRepository;
    }

    public void createProduct(Product product) {
        validateProduct(product);
        ProductRepository.save(product);
    }


    private  void validateProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("商品信息不能为空");
        }
        if (product.getId() == null) {
            throw new IllegalArgumentException("商品ID不能为空");
        }
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("商品名称不能为空");
        }
        if (product.getPrice() == null || product.getPrice() <= 0) {
            throw new IllegalArgumentException("商品价格必须大于0");
        }
        if (product.getStock() == null || product.getStock() < 0) {
            throw new IllegalArgumentException("商品库存不能为负数");
        }
    }

}
