package com.student.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Reflectable {
    String desc() default "允许被反射工具操作的类";
}

//还有一个PARAMETER方法参数级的但感觉用的范围实在少