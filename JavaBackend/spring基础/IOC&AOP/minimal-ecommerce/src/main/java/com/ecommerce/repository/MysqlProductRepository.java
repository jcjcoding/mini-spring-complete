package com.ecommerce.repository;

import com.ecommerce.entity.Product;
import com.ecommerce.ioc.Component;
import com.ecommerce.ioc.PostConstruct;
import com.ecommerce.ioc.PreDestroy;

import java.util.HashMap;
import java.util.Map;

@Component("mysqlRepo")
public class MysqlProductRepository implements ProductRepository {
    private static final Map<Long, Product> productDB = new HashMap<>();
    //哈希表模拟数据库
    private String dbConnection;// = "jdbc:mysql://localhost:3306/ecommerce";

    //初始化方法
    @PostConstruct
    public void initDbConnection() {
        this.dbConnection = "MySQL连接[id=" + System.currentTimeMillis() + "]";
        System.out.println("[MySQLRepo初始化] 建立数据库连接：" + this.dbConnection);
    }

    //销毁方法
    @PreDestroy
    public void closeDbConnection() {
        System.out.println("[MySQLRepo销毁] 关闭数据库连接：" + dbConnection);
        this.dbConnection = null;
    }

    //存
    @Override
    public void save(Product product) {
        if(product == null) {
            throw new IllegalStateException("product is null");
        }
        productDB.put(product.getId(), product);
        System.out.println("保存商品到数据库 商品信息如下：" + product);
    }

    //取
    public Product findById(Long id) {
        if (id == null) {
           return null;
        }
        Product product = productDB.get(id);
        if (product == null) {
            System.out.println("未找到id为"+id+"商品");
        }else{
            System.out.println("找到该商品 商品信息如下："+product);
        }
        return product;
    }
}
