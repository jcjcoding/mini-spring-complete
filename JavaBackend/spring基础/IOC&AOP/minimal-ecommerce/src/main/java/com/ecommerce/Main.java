package com.ecommerce;

import com.ecommerce.entity.Product;
import com.ecommerce.ioc.BeanDefinition;
import com.ecommerce.ioc.BeanFactory;
import com.ecommerce.ioc.ClassPathBeanDefinitionScanner;
import com.ecommerce.ioc.DefaultBeanFactory;
import com.ecommerce.repository.MysqlProductRepository;
import com.ecommerce.service.ProductService;
import com.ecommerce.service.ProductServiceInterface;

public class Main {
    public static void main(String[] args) throws Exception {
        // 1. 创建容器（自动集成AOP）
        BeanFactory beanFactory = new DefaultBeanFactory();

        // 2. 包扫描：包含ProductLogAspect切面类
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(beanFactory);
        scanner.scan("com.ecommerce");

        // 3. 获取代理对象（注意：这里需要通过接口接收，因为代理对象实现了接口）
        ProductServiceInterface productService = beanFactory.getBean(ProductServiceInterface.class);

        // 4. 测试正常流程
        System.out.println("===== 测试正常创建商品 =====");
        Product validProduct = new Product(1001L, "无线鼠标", 59.9, 100);
        productService.createProduct(validProduct);

        // 5. 测试异常流程（价格为负数，触发校验失败）
        System.out.println("\n===== 测试创建商品异常 =====");
        Product invalidProduct = new Product(1002L, "有线键盘", -99.0, 50);
        try {
            productService.createProduct(invalidProduct);
        } catch (Exception e) {
            // 异常已被AOP捕获并打印，这里无需额外处理
        }

        // 6. 关闭容器
        beanFactory.close();
    }
    /*
    public static void main(String[] args) throws Exception {
        // 1. 创建IOC容器
        BeanFactory beanFactory = new DefaultBeanFactory();

        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(beanFactory);
        scanner.scan("com.ecommerce");
        ProductServiceInterface productService = beanFactory.getBean(ProductServiceInterface.class);
        // 2. 向容器注册Bean定义
        // 注册ProductRepository（数据层Bean）
        /*
        beanFactory.registerBeanDefinition(
                "productRepository",  // Bean名称
                new BeanDefinition(MysqlProductRepository.class, "productRepository", true)  // 单例
        );

        // 注册ProductService（业务层Bean）
        beanFactory.registerBeanDefinition(
                "productService",  // Bean名称
                new BeanDefinition(ProductService.class, "productService", true)  // 单例
        );
        */
        /*
        // 3. 从容器获取Bean实例
        ProductService productService = beanFactory.getBean(ProductService.class);
        ProductRepository productRepository = beanFactory.getBean(ProductRepository.class);

        // 4. 手动注入依赖（阶段1暂时手动调用setter，后续阶段会自动注入）
        productService.setProductRepository(productRepository);

        // 5. 测试业务流程（创建商品）
        Product product = new Product(1001L, "无线鼠标", 59.9, 100);
        productService.createProduct(product);  // 预期输出：[DB] 商品保存成功...

        // 6. 验证单例模式（多次获取是否为同一实例）
        ProductService service1 = (ProductService) beanFactory.getBean("productService");
        ProductService service2 = (ProductService) beanFactory.getBean("productService");
        System.out.println("service1和service2是否为同一实例：" + (service1 == service2));  // 预期true


        // 3. 从容器获取ProductService（此时依赖已自动注入）
        //ProductService productService = beanFactory.getBean(ProductService.class);

        // 4. 直接调用业务方法（无需手动注入依赖）
        Product product = new Product(1001L, "无线鼠标", 59.9, 100);
        productService.createProduct(product); // 预期正常执行，无空指针
        Product invalidProduct = new Product(1002L, "有线键盘", -99.0, 50);
        try {
            productService.createProduct(invalidProduct);
        } catch (Exception e) {
            // 异常已被AOP捕获并打印，这里无需额外处理
        }
        beanFactory.close();
    }
    */
}