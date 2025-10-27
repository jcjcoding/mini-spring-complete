package com.ecommerce.ioc;

public interface BeanFactory {
    /**
     * 注册Bean定义到容器
     * @param beanName Bean的名称
     * @param beanDefinition Bean的元信息
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    /**
     * 根据名称从容器获取Bean实例
     * @param beanName Bean的名称
     * @return Bean实例
     */
    Object getBean(String beanName) throws Exception;

    /**
     * 简化：根据类型从容器获取Bean实例（方便调用）
     * @param requiredType Bean的类型
     * @return Bean实例
     */
    <T> T getBean(Class<T> requiredType) throws Exception;

    void close();
}
