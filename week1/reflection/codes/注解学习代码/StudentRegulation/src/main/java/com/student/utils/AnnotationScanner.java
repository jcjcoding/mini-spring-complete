package com.student.utils;

import com.student.annotation.Reflectable;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 注解扫描器：扫描指定包下所有带有@Reflectable注解的类
 */
public class AnnotationScanner {
    /**
     * 获取类上@Component注解的name值
     * @param clazz 类的字节码对象
     * @return Bean名称（如果没有@Component注解，返回null）
     */
    //目前局限性强可以说仅做学习用
    public static String getComponentName(Class<?> clazz) {
        // 1. 获取类上的@Component注解
        Reflectable component = clazz.getAnnotation(Reflectable.class);
        // 2. 如果注解存在，返回其desc值；否则返回null
        if (component != null) {
            return component.desc();
        }
        return null;
    }


    //网上找的代码但感觉超过目前水平有点多了，但是觉得写的挺好的以后可以回来看看学学就不删除了
    /**
     * 扫描指定包下所有带有@Reflectable注解的类
     * @param packageName 要扫描的包路径（例如："com.student.entity"）
     * @return 带有@Reflectable注解的类的集合
     * @throws IOException 资源读取异常
     * @throws ClassNotFoundException 类加载异常
     * @throws URISyntaxException 格式异常
     */
    /*
    public static List<Class<?>> scanReflectableClasses(String packageName)
            throws IOException, ClassNotFoundException, URISyntaxException {

        List<Class<?>> result = new ArrayList<>();
        // 1. 将包路径转为资源路径（com.student.entity → com/student/entity）
        String packagePath = packageName.replace(".", "/");

        // 2. 通过类加载器获取包下所有资源（.class文件）
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> resources = classLoader.getResources(packagePath);

        // 3. 遍历所有资源（可能有多个路径，比如不同的jar包或目录）
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            // 只处理文件系统中的类（简化场景，暂不处理jar包内的类）
            if ("file".equals(resource.getProtocol())) {
                // 4. 解析资源路径为文件目录，遍历目录下所有.class文件
                File packageDir = new File(resource.toURI());
                scanClassesInDir(packageDir, packageName, result);
            }
        }

        return result;

    }
    */
    /**
     * 递归扫描目录下的所有.class文件，检查是否带有@Reflectable注解
     * @param dir 包对应的目录
     * @param packageName 包名（用于拼接类的全限定名）
     * @param result 收集结果的集合
     */
    /*
    private static void scanClassesInDir(File dir, String packageName, List<Class<?>> result)
            throws ClassNotFoundException {

        if (dir == null || !dir.exists()) {
            return;
        }

        // 遍历目录下的所有文件/子目录
        File[] files = dir.listFiles();
        if (files == null) {
            return;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                // 如果是子目录，递归扫描（处理子包，例如com.student.entity.sub）
                String subPackageName = packageName + "." + file.getName();
                scanClassesInDir(file, subPackageName, result);
            } else if (file.getName().endsWith(".class")) {
                // 如果是.class文件，解析类名并加载
                String className = packageName + "." +
                        file.getName().substring(0, file.getName().length() - 6); // 去掉.class后缀

                // 加载类（使用当前类加载器）
                Class<?> clazz = Class.forName(className);

                // 检查是否带有@Reflectable注解
                if (clazz.isAnnotationPresent(Reflectable.class)) {
                    result.add(clazz);
                }
            }
        }
    }
    */

}
