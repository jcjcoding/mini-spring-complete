package com.ecommerce.ioc;

public class BeanDefinition {
    private Class<?> beanClass;
    private String beanName;
    private boolean singleton;

    public BeanDefinition(Class<?> beanClass, String beanName, boolean singleton) {
        this.beanClass = beanClass;
        this.beanName = beanName;
        this.singleton = singleton;
    }

    public Class<?> getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public boolean isSingleton() {
        return singleton;
    }

    public void setSingleton(boolean singleton) {
        this.singleton = singleton;
    }
}
