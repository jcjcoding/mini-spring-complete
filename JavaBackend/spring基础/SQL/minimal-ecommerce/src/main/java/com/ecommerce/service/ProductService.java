package com.ecommerce.service;

import com.ecommerce.entity.Product;
import com.ecommerce.ioc.*;
import com.ecommerce.repository.ProductRepository;

import java.util.List;

@Component
public class ProductService implements ProductServiceInterface{
    @Autowired
    @Qualifier("mysqlRepo")
    private ProductRepository ProductRepository;

    public void createProduct(Product product) {
        try {
            validateProduct(product);
            ProductRepository.save(product);
        } catch (Exception e) {

        }
    }

    public Product getProductById(Long id) {
        if (id == null || id <= 0) {
            throw new RuntimeException("商品ID无效（必须为正整数）");
        }
        Product product = ProductRepository.findById(id);
        if (product == null) {
            throw new RuntimeException("商品不存在（ID：" + id + "）");
        }
        return product;
    }

    public List<Product> showAll() {
        try {
            List<Product> products = ProductRepository.findAll(); // 可能抛出异常（如SQL异常）

            if (products.isEmpty()) {
                throw new RuntimeException("商品表为空，暂无数据");
            }

            for (Product product : products) {
                System.out.println(product);
            }

            return products;
        } catch (Exception e) {
            throw new RuntimeException("查询商品失败：" + e.getMessage());
        }
    }

    public String buyProduct(Long productId, Integer quantity){
        if (productId == null || productId <= 0) {
            throw new RuntimeException("商品ID无效（必须为正整数）");
        }

        if (quantity == null || quantity <= 0) {
            throw new RuntimeException("购买数量无效（必须为正整数）");
        }

        Product product = ProductRepository.findById(productId);
        if (product == null) {
            throw new RuntimeException("购买失败：商品不存在（ID：" + productId + "）");
        }

        int affectRows = ProductRepository.deductStock(productId, quantity);
        if (affectRows == 0) {
            throw new RuntimeException("购买失败：库存不足（当前库存：" + product.getStock() + "，需购买：" + quantity + "）");
        }

        Product updatedProduct = ProductRepository.findById(productId);
        return "购买成功！商品：" + updatedProduct.getName() + "，购买数量：" + quantity + "，剩余库存：" + updatedProduct.getStock();
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
        if (ProductRepository.findById(product.getId()) != null) {
            throw new IllegalArgumentException("商品已经存在");
        }
    }



}
