package com.student.test;

import com.student.entity.Student;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class TestField {
    public static void main(String[] args) throws Exception {
        Class<?> studentClass = Student.class;
        Constructor<?> constructor = studentClass.getDeclaredConstructor(String.class, int.class);
        constructor.setAccessible(true);
        Student student =(Student) constructor.newInstance("小白",23);
        //student(小白，23)

        Field nameField = studentClass.getDeclaredField("name");
        nameField.setAccessible(true);

        String oldname = (String) nameField.get(student);
        System.out.println("修改前名字"+ oldname);

        nameField.set(student,"White");

        String newname = (String) nameField.get(student);
        System.out.println("修改后名字"+ newname);
    }
}
