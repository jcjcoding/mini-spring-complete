package com.student.test;

import com.student.entity.Student;
import com.student.service.StudentService;
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

        Class<?> studentService = StudentService.class;
        Object service = ReflectionUtils.creatInstance(
                studentService,
                new Class[]{},
                new Object[]{}
        );
        System.out.println("学生服务类生成"+service);

        ReflectionUtils.invokeMethod(service,"addStudent",
                new Class[]{String.class,int.class},new Object[]{"小黑",15});
        ReflectionUtils.invokeMethod(service,"deleteStudent",
                new Class[]{String.class},new Object[]{"小黑"});
        ReflectionUtils.invokeMethod(service,"queryStudent",
                new Class[]{String.class},new Object[]{"小黑"});
    }
}
