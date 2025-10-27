package com.ecommerce.ioc;

import com.ecommerce.aop.ProxyFactory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class DefaultBeanFactory implements BeanFactory {
    private final ProxyFactory proxyFactory;

    public DefaultBeanFactory() {
        this.proxyFactory = new ProxyFactory(this); // 传入当前容器
    }

    private final Map<String, BeanDefinition> beanDefinitionMap = new HashMap<String, BeanDefinition>();

    private final Map<String,Object> singletonObjects = new HashMap<>();

    private  Object createBean(BeanDefinition beanDefinition) throws Exception {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Object bean = beanClass.getDeclaredConstructor().newInstance();
        //注入依赖
        autowireDependencies(bean);
        
        //执行初始化
        invokePostConstruct(bean);

        //代理
        Object proxy = proxyFactory.createProxy(bean);
        return proxy;
    }

    private void invokePostConstruct(Object bean) throws Exception {
        Class<?> beanClass = bean.getClass();
        // 遍历所有方法，找到带@PostConstruct的方法
        for (java.lang.reflect.Method method : beanClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(PostConstruct.class)) {
                method.setAccessible(true); // 允许调用private方法
                method.invoke(bean); // 执行初始化方法（无参）
                System.out.println("执行初始化方法：" + beanClass.getSimpleName() + "." + method.getName() + "()");
            }
        }
    }


    private void autowireDependencies(Object bean) throws Exception {
        Class<?> beanClass = bean.getClass();

        Field[] fields = beanClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Autowired.class)) {
                //获取字段类型
                Class<?> dependencyType = field.getType();

                Qualifier qualifier = field.getAnnotation(Qualifier.class);
                Object dependency;
                if (qualifier != null) {
                    String beanName = qualifier.value();
                    dependency = getBean(beanName);
                    if(!dependencyType.isInstance(dependency)) {
                        throw new Exception("Bean" + beanName + "的类型不匹配" + dependencyType);
                    }
                } else {
                    dependency = getBean(dependencyType);
                }

                field.setAccessible(true);
                field.set(bean, dependency);
                System.out.println("自动注入成功：" + bean.getClass().getSimpleName() +
                        "的字段" + field.getName() + "注入了" + dependency);
            }
        }
    }
    
    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName, beanDefinition);
    }

    @Override
    public Object getBean(String beanName) throws Exception {
        //找到Bean定义
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition == null) {
            throw new Exception("Bean not found: " + beanName);
        }

        // 2. 处理单例：如果是单例且已创建，直接返回缓存的实例
        if (beanDefinition.isSingleton()) {
            Object singletonBean = singletonObjects.get(beanName);
            if (singletonBean != null) {
                return singletonBean;
            }
            // 3. 单例未创建：创建实例并缓存
            Object newBean = createBean(beanDefinition);
            singletonObjects.put(beanName, newBean);
            return newBean;
        } else {
            // 非单例：每次获取都创建新实例（默认单例）
            return createBean(beanDefinition);
        }
    }

    @Override
    public <T> T getBean(Class<T> requiredType) throws Exception {
        // 遍历所有Bean定义，找到类型匹配的Bean（简化实现）
        for (String beanName : beanDefinitionMap.keySet()) {
            BeanDefinition definition = beanDefinitionMap.get(beanName);
            if (requiredType.isAssignableFrom(definition.getBeanClass())) {
                return (T) getBean(beanName);
            }
        }
        throw new Exception("容器中没有类型为[" + requiredType + "]的Bean");
    }
    @Override
    public void close() {
        try {
            invokePreDestroy(); // 执行所有单例Bean的销毁方法
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void invokePreDestroy() throws Exception {
        // 遍历所有单例Bean
        for (Map.Entry<String, Object> entry : singletonObjects.entrySet()) {
            Object bean = entry.getValue();
            Class<?> beanClass = bean.getClass();
            // 查找带@PreDestroy的方法
            for (java.lang.reflect.Method method : beanClass.getDeclaredMethods()) {
                if (method.isAnnotationPresent(PreDestroy.class)) {
                    method.setAccessible(true);
                    method.invoke(bean); // 执行销毁方法（无参）
                    System.out.println("执行销毁方法：" + beanClass.getSimpleName() + "." + method.getName() + "()");
                }
            }
        }
        // 清空单例缓存（可选）
        singletonObjects.clear();
    }
    /**
     * 创建Bean实例（通过类的无参构造器）
     */

}
