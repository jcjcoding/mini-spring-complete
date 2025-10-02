package com.student.test;

import com.student.entity.Student;

import java.lang.reflect.Constructor;

public class TestConstructor {
    public static void main(String[] args) throws Exception {
        Class<?> studentClass = Student.class;
        System.out.println("获取的class对象:"+ studentClass);
        //无参实例创建
        Constructor<?> constructor = studentClass.getConstructor();

        Student student1 = (Student) constructor.newInstance();
        //这里之所以要用（Student）强调，是因为前面用的<?>修饰对于电脑来说constructor是模糊的不能和左边的Student对应上，所以要强调来左右对应
        System.out.println("无参实例创建成功"+student1);

        //有参实例创建+public权限
        Constructor<?> constructorPublic = studentClass.getConstructor(String.class);

        Student student2 = (Student) constructorPublic.newInstance("小白");
        System.out.println("public下的有参实例创建"+student2+":"+student2.getName());

        //有参实例创建+private权限 重点！重点！重点！
        Constructor<?> constructorPrivate = studentClass.getDeclaredConstructor(String.class, int.class);
        //这里我们用的getDeclaredConstructor而不是getConstructor
        constructorPrivate.setAccessible(true);
        //用于突破权限限制

        Student student3 = (Student) constructorPrivate.newInstance("小黑",19);
        System.out.println("private下的有参实例创建"+student3+":"+student3.getName()+student3.getAge());

    }
}
