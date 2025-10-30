package com.ecommerce.test;

import com.ecommerce.entity.Product;
import com.ecommerce.repository.MysqlProductRepository;

// 测试类：单独验证数据层是否能连接MySQL并写入数据
public class TestMysqlRepo {
    public static void main(String[] args) {
        // 1. 创建数据层实例（不通过IOC，直接new）
        MysqlProductRepository repo = new MysqlProductRepository();

        try {
            // 2. 手动调用初始化方法（创建连接池）
            repo.init();

            // 3. 创建测试商品（ID必须唯一，重复会报主键冲突）
            Product testProduct = new Product(
                    1001L,       // 商品ID（比如1001，下次测试改1002）
                    "无线鼠标",   // 商品名称
                    59.9,        // 商品价格（59.9元）
                    100          // 商品库存（100个）
            );

            // 4. 调用save方法，写入MySQL
            repo.save(testProduct);

        } catch (Exception e) {
            // 5. 若失败，打印错误信息（方便排查）
            System.out.println("❌ 测试失败：" + e.getMessage());
        } finally {
            // 6. 无论成功失败，都关闭连接池
            repo.destroy();
        }
    }
}