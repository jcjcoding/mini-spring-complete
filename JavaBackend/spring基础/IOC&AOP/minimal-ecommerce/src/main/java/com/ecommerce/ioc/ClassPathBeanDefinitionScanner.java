package com.ecommerce.ioc;

import java.io.File;
import java.net.URL;
import java.util.Enumeration;

public class ClassPathBeanDefinitionScanner {
    private final BeanFactory beanFactory;

    public ClassPathBeanDefinitionScanner(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
    /**
     * 扫描指定包并注册Bean
     * @param basePackage 基础包名（如"com.ecommerce"）
     */
    public void scan(String basePackage) throws Exception {
        String packagePath = basePackage.replace('.', '/');

        //这里用上下文类加载器突破双亲委派机制跨层级加载类
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> resources = classLoader.getResources(packagePath);
        //用Enumeration遍历包下所有文件

        //遍历文件
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            if("file".equals(resource.getProtocol())) {
                //扫描，class文件
                scanDirectory(new File(resource.toURI()),basePackage);
                //传两个参，相互验证
            }
        }
    }

    private void scanDirectory(File dir, String basePackage) throws Exception {
        if(!dir.exists()) return;

        File[] files = dir.listFiles();
        if(files == null) return;

        for (File file : files) {
            if(file.isDirectory()) {
                //判断是否是目录，并且一步步递进到子目录直到可以读写的文件
                String subPackage= basePackage + "." + file.getName();
                scanDirectory(file, subPackage);
            }else if(file.getName().endsWith(".class")) {
                String className = basePackage + "." + file.getName().replace(".class", "");
                Class<?> clazz = Class.forName(className);

                //检查是否有注解
                if(clazz.isAnnotationPresent(Component.class)) {
                    Component component = clazz.getAnnotation(Component.class);
                    //确定bean名
                    String beanName = component.value().isEmpty()
                            ? lowerFirstChar(clazz.getSimpleName())
                            : component.value();

                    //增加依赖
                    BeanDefinition beanDefinition = new BeanDefinition(clazz,beanName,true);
                    beanFactory.registerBeanDefinition(beanName,beanDefinition);
                    System.out.println("包扫描自动注册Bean：" + beanName + "（" + clazz.getName() + "）");                }
            }
        }
    }

    private String lowerFirstChar(String className) {
        if(className == null || className.isEmpty()) return "";
        char firstChar = className.charAt(0);
        return Character.toLowerCase(firstChar) + className.substring(1);
    }
}
