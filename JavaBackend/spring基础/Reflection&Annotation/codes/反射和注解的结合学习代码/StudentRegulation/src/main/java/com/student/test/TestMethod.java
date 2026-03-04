package com.student.test;

import com.student.entity.Student;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class TestMethod {
    public static void main(String[] args) throws Exception {
        Class<?> studentClass = Student.class;
        Constructor<?> constructor = studentClass.getDeclaredConstructor(String.class,int.class);
        constructor.setAccessible(true);
        Student student = (Student) constructor.newInstance("小黑",19);
        System.out.println(student+":"+student.getName()+":"+student.getAge());
        //这节的核心Method获取private里面的方法
        Method getInfoMethod = studentClass.getDeclaredMethod("getInfo");
        getInfoMethod.setAccessible(true);
        String info = (String) getInfoMethod.invoke(student);
        System.out.println("运行结果"+info);
        //带参的方法提取
        Method setNameMethod = studentClass.getDeclaredMethod("setNamePrivate", String.class);
        setNameMethod.setAccessible(true);
        setNameMethod.invoke(student,"小白");
        info = (String) getInfoMethod.invoke(student);
        System.out.println("运行结果"+info);
    }
}
