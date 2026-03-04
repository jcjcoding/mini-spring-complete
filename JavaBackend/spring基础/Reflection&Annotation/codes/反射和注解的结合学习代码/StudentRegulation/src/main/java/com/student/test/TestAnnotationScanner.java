package com.student.test;

import com.student.entity.Student;
import com.student.utils.AnnotationScanner;
import com.student.utils.ReflectionUtils;
import java.util.List;

public class TestAnnotationScanner{
    public static void main(String[] args) {
        AnnotationScanner annotationScanner = new AnnotationScanner();
        System.out.println("注解日志:"+annotationScanner.getComponentName(Student.class));
    }
        //网上找的代码的测试代码（也是找的）
        /*
        try {
            // 1. 扫描com.student.entity包下所有带@Reflectable的类
            List<Class<?>> reflectableClasses = AnnotationScanner.scanReflectableClasses("com.student.entity");

            // 2. 遍历这些类，用ReflectionUtils批量反射操作
            for (Class<?> clazz : reflectableClasses) {
                System.out.println("找到带@Reflectable的类：" + clazz.getName());

                // 示例1：反射创建实例（调用无参构造器）
                Object instance = ReflectionUtils.creatInstance(clazz, new Class[]{}, new Object[]{});
                System.out.println("创建实例：" + instance);

                // 示例2：反射修改字段（假设类有name字段）
                if (clazz.getDeclaredField("name") != null) {
                    ReflectionUtils.setFieldValue(instance, "name", "反射修改的名字");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    */
}