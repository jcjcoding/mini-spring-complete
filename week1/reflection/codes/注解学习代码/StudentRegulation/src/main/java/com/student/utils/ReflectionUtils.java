package com.student.utils;

import com.student.annotation.Reflectable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionUtils {
    //我们在前面annotation包里面写下注解，在这里检测我们读写的类有没有reflectable，来保证反射对象合法
    private static void checkReflectable(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Reflectable.class)) {
            throw new SecurityException("类 " + clazz.getName() + " 未标记@Reflectable，禁止反射操作");
        }
    }
    //通过构造器突破限制创造实例
    public static Object creatInstance(Class<?> clazz,Class<?>[] paramTypes,Object[] params) throws Exception{
        checkReflectable(clazz);
        Constructor<?> constructor = clazz.getDeclaredConstructor(paramTypes);
        constructor.setAccessible(true);
        return constructor.newInstance(params);
    }
    //通过filed读写值
    public static void setFieldValue(Object object,String fieldName,Object value) throws Exception{
        checkReflectable(object.getClass());
        Class<?> clazz = object.getClass();
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(object,value);
    }
    //通过Method使用方法
    public static Object invokeMethod(Object object,String methodName,Class<?>[] paramsTypes,Object[] params) throws Exception{
        checkReflectable(object.getClass());
        Class<?> clazz = object.getClass();
        Method method = clazz.getDeclaredMethod(methodName, paramsTypes);
        method.setAccessible(true);
        return method.invoke(object, params);
    }
}
