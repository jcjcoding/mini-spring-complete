package com.student.entity;

import com.student.annotation.Reflectable;

@Reflectable(desc = "学生类允许反射创建实例")
public class Student {

    private String name;
    private int age;
    private String id;

    // 无参构造器 很重要！重要！重要！能确保后面的反射操作不报错
    public Student() {}

    //后续反射测试带参构造器
    public Student(String name){
        this.name = name;
    }

    //用private 后续反射测试突破private权限
    private Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    //用private 后续反射测试Method方法
    private String getInfo() {
        return "name: " + name + ", age: " + age;
    }

    private void setNamePrivate(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}
}


