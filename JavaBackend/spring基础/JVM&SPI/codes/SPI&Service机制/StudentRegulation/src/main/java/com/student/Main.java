package com.student;

import com.student.service.ServiceFactory;
import com.student.service.StudentService;

public class Main {
    public static void main(String[] args) {
        // 通过工厂获取学生服务（无需关心实现类，全靠SPI配置）
        StudentService studentService = ServiceFactory.getService(StudentService.class);
    }
}