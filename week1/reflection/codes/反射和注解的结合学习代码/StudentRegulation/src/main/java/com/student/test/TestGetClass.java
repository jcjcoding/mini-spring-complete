package com.student.test;

import com.student.entity.Student;

public class TestGetClass {
    public static void main(String[] args) {
        try{
            /*
            Class<?> clazz = Class.forName("com.student.entity.Student");
            //直接通过全类名加载类
             */
            /*
            Student student = new Student();
            Class<?> clazz = student.getClass();
            //通过实例获取
             */
            Class<?> clazz = Student.class;
            //直接通过类名获取
            System.out.println("获取的class对象:"+clazz);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
