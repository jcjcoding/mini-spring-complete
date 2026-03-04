package com.ecommerce.aop;

import com.ecommerce.entity.Product;
import com.ecommerce.ioc.Component;

/**
 * 商品模块的日志切面：包含对createProduct方法的增强逻辑
 */
@Component // 让切面类被容器管理（需要包扫描注册）
public class ProductLogAspect implements Aspect {
    // 前置通知：createProduct执行前打印参数
    @Before("createProduct") // 切入点：匹配方法名是createProduct的方法
    public void logBefore(Object[] args) { // 参数：目标方法的入参（这里是Product对象）
        Product product = (Product) args[0]; // 目标方法只有一个Product参数
        System.out.println("[AOP前置通知] 准备创建商品：" + product);
    }

    // 后置通知：createProduct执行后打印耗时（需要记录开始时间，这里简化用局部变量）
    @After("createProduct")
    public void logAfter(long startTime) { // 参数：目标方法执行的开始时间（后续代理逻辑传入）
        long cost = System.currentTimeMillis() - startTime;
        System.out.println("[AOP后置通知] 商品创建完成，耗时：" + cost + "ms");
    }

    // 异常通知：createProduct抛出异常时打印错误
    @AfterThrowing("createProduct")
    public void logException(Exception e) { // 参数：目标方法抛出的异常
        System.out.println("[AOP异常通知] 商品创建失败：" + e.getMessage());
    }
}