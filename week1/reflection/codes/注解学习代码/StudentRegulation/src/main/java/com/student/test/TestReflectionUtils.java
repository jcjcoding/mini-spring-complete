package com.student.test;

import com.student.entity.Student;
import com.student.utils.ReflectionUtils;

public class TestReflectionUtils {
    public static void main(String[] args) throws Exception {
        Class<?> studentClass = Student.class;
        Object student = ReflectionUtils.creatInstance(
                studentClass,
                new Class[]{String.class,int.class},
                new Object[]{"工具类测试",234}
        );
        System.out.println("工具测试实例:"+student);

        ReflectionUtils.setFieldValue(student,"name","修改过的名字");

        String info = (String) ReflectionUtils.invokeMethod(
                student,
                "getInfo",
                new Class[]{},
                new Object[]{}
        );
        System.out.println("调用私有方法结果"+info);
    }
}
