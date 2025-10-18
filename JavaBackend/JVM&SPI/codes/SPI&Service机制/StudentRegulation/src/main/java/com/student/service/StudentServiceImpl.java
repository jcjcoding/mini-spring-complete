package com.student.service;


import com.student.annotation.Service;
import com.student.entity.Student;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Override
    public List<Student> getByName(String studentName){
        List<Student> students = new ArrayList<Student>();
        return students;
    }

    @Override
    public Student findById(String s) {
        return null;
    }

    @Override
    public Boolean save(Student entity) {
        return null;
    }

    @Override
    public Boolean update(Student entity) {
        return null;
    }

    @Override
    public Boolean delete(String s) {
        return null;
    }
}

/*
import com.student.annotation.Log;
import com.student.annotation.Reflectable;
import com.student.entity.Student;

@Reflectable(desc = "学生服务类允许被反射工具操作")

public class StudentServiceImpl{

    @Log("日志:添加学生信息:记录学生姓名,年龄")
    public void addStudent(String name, int age) {
        //方法暂时不完善，目前的目的依旧是学习巩固知识
        System.out.println("学生添加成功:"+name);
    }

    @Log("日志:删除学生信息")
    public void deleteStudent(String name) {
        System.out.println("学生删除成功:"+name);
    }

    @Log()
    public Student queryStudent(String name) {
        return new Student(name);
        //模拟返回
    }

}
 */