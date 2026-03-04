package com.ecommerce;

import com.ecommerce.entity.Product;
import com.ecommerce.ioc.BeanFactory;
import com.ecommerce.ioc.ClassPathBeanDefinitionScanner;
import com.ecommerce.ioc.DefaultBeanFactory;
import com.ecommerce.service.ProductServiceInterface;

public class Main {
    public static void main(String[] args) throws Exception {
        // 创建容器（自动集成AOP）
        BeanFactory beanFactory = new DefaultBeanFactory();

        // 包扫描：包含ProductLogAspect切面类
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(beanFactory);
        scanner.scan("com.ecommerce");

        // 获取代理对象
        ProductServiceInterface productService =(ProductServiceInterface)beanFactory.getBean(ProductServiceInterface.class);

        // 测试正常流程
        System.out.println("===== 测试正常创建商品 =====");
        Product validProduct = new Product(1004L, "无线鼠标", 59.9, 100);

        productService.createProduct(validProduct);

        // 测试异常流程（价格为负数，触发校验失败）
        System.out.println("\n===== 测试创建商品异常 =====");
        Product invalidProduct = new Product(1007L, "有线键盘", -99.0, 50);

        productService.createProduct(invalidProduct);


        // 测试service功能
        productService.showAll();

        productService.buyProduct(1004L,2);
        // 关闭容器
        beanFactory.close();
    }
}