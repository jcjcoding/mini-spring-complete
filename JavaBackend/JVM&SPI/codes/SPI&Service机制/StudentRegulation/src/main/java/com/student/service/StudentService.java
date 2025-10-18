package com.student.service;


import com.student.entity.Student;

import java.util.List;

public interface StudentService extends BaseService<Student,String> {
    //根据名字查询学生
    List<Student> getByName(String studentName);
}
