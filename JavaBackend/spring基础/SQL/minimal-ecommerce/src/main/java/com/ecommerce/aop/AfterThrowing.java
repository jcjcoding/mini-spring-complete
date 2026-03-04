package com.ecommerce.aop;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface AfterThrowing {
    String value(); // 切入点表达式
    String throwing();
}