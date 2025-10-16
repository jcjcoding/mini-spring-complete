package com.student.service;

import com.student.utils.HashMap;


// 单例的服务工厂（直接作为顶级类，避免内部类的静态成员问题）
public class ServiceFactory {

    // 1. 静态变量：存储工厂唯一实例（volatile保证多线程可见性）
    private static volatile ServiceFactory instance;

    // 2. 缓存容器：存储已创建的服务实例（key为服务接口/类，value为实例）
    private final HashMap<Class<? extends Service>, Service> serviceCache;

    // 3. 私有构造方法：禁止外部直接创建实例，初始化缓存
    private ServiceFactory() {
        serviceCache = new HashMap<>(); // 初始化缓存（假设自定义HashMap兼容java.util.Map）
    }

    // 4. 单例获取方法：双重检查锁（DCL）保证线程安全且高效
    public static ServiceFactory getInstance() {
        // 第一次检查：避免频繁加锁（提高效率）
        if (instance == null) {
            // 加锁：保证只有一个线程进入初始化逻辑
            synchronized (ServiceFactory.class) {
                // 第二次检查：防止多线程同时通过第一次检查后重复创建实例
                if (instance == null) {
                    instance = new ServiceFactory();
                }
            }
        }
        return instance;
    }

    // 5. 核心方法：根据服务类获取实例（缓存复用，避免重复创建）
    public <T extends Service> T getService(Class<T> serviceClass) throws Exception {
        // 先查缓存，存在则直接返回
        if (serviceCache.containsKey(serviceClass)) {
            return (T) serviceCache.get(serviceClass);
        }

        // 缓存不存在，通过反射创建实例（需要服务类有默认构造方法）
        T service = serviceClass.getConstructor().newInstance();

        // 存入缓存，下次复用
        serviceCache.put(service.hashCode(),serviceClass, service);

        return service;
    }
}
