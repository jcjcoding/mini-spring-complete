package com.ecommerce.aop;

import com.ecommerce.ioc.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

@Component
public class LogAspect implements Aspect{
    @Before("within(com.ecommerce.service..*)")
    public void beforeMethod(Method targetMethod, Object[] args) {
        String methodName = targetMethod.getName();
        String argsStr = Arrays.toString(args);
        System.out.printf("[AOP前置日志] 方法：%s，参数：%s%n", methodName, argsStr);
    }

    @Around("within(com.ecommerce.service..*)")
    public Object aroundMethod(Method targetMethod, Object[] args,Object target) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object result = targetMethod.invoke(target, args);

        long endTime = System.currentTimeMillis();
        long costTime = endTime - startTime;
        System.out.printf("[AOP环绕日志] 方法：%s，执行耗时：%dms%n", targetMethod.getName(), costTime);

        return result;
    }

    @AfterThrowing(value = "within(com.ecommerce.service..*)", throwing = "e")
    public void afterThrowingMethod(Method targetMethod, Exception e) {
        String methodName = targetMethod.getName();
        String exceptionMsg = e.getMessage();
        System.out.printf("[AOP异常日志] 方法：%s，异常：%s%n", methodName, exceptionMsg);
        // 可扩展：此处可添加“异常入库”“报警”等逻辑
    }
}
