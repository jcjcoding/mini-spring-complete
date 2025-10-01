package com.student.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionUtils {
    //通过构造器突破限制创造实例
    public static Object creatInstance(Class<?> clazz,Class<?>[] paramTypes,Object[] params) throws Exception{
        Constructor<?> constructor = clazz.getDeclaredConstructor(paramTypes);
        constructor.setAccessible(true);
        return constructor.newInstance(params);
    }
    //通过filed读写值
    public static void setFieldValue(Object object,String fieldName,Object value) throws Exception{
        Class<?> clazz = object.getClass();
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(object,value);
    }
    //通过Method使用方法
    public static Object invokeMethod(Object object,String methodName,Class<?>[] paramsTypes,Object[] params) throws Exception{
        Class<?> clazz = object.getClass();
        Method method = clazz.getDeclaredMethod(methodName, paramsTypes);
        method.setAccessible(true);
        return method.invoke(object, params);
    }
}
