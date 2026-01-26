```Java
//一、基础类型数组排序
import java.util.Arrays;

public class ArraySortDemo {
    public static void main(String[] args) {
        // 1. int数组升序（默认）
        int[] intArr = {5, 2, 9, 1, 5, 6};
        Arrays.sort(intArr);
        System.out.println("int数组升序：" + Arrays.toString(intArr)); 
        // 输出：[1, 2, 5, 5, 6, 9]
        
        // 2. 字符串数组升序（字典序）
        String[] strArr = {"banana", "apple", "cherry", "date"};
        Arrays.sort(strArr);
        System.out.println("字符串数组升序：" + Arrays.toString(strArr)); 
        // 输出：[apple, banana, cherry, date]
        
        // 3. Integer数组降序（包装类 + Lambda比较器）
        Integer[] integerArr = {5, 2, 9, 1, 5, 6};
        Arrays.sort(integerArr, (a, b) -> b - a);
        System.out.println("Integer数组降序：" + Arrays.toString(integerArr)); 
        // 输出：[9, 6, 5, 5, 2, 1]
    }
}




//二、List 集合排序


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListSortDemo {
    public static void main(String[] args) {
        // 1. List<Integer> 升序
        List<Integer> numList = new ArrayList<>();
        numList.add(5);
        numList.add(2);
        numList.add(9);
        Collections.sort(numList);
        System.out.println("List<Integer>升序：" + numList); 
        // 输出：[2, 5, 9]
        
        // 2. List<String> 降序（自定义比较器）
        List<String> strList = new ArrayList<>();
        strList.add("banana");
        strList.add("apple");
        strList.add("cherry");
        Collections.sort(strList, (s1, s2) -> s2.compareTo(s1));
        System.out.println("List<String>降序：" + strList); 
        // 输出：[cherry, banana, apple]
    }
}




//三、自定义对象排序（实现 Comparable 接口）

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// 学生类：实现Comparable，默认按年龄升序
class Student implements Comparable<Student> {
    private String name;
    private int age;

    // 构造方法
    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // 核心：重写compareTo定义排序规则
    @Override
    public int compareTo(Student o) {
        // 返回值规则：
        // 正数 → 当前对象 > 传入对象；0 → 相等；负数 → 当前对象 < 传入对象
        return this.age - o.age; // 年龄升序（o.age - this.age 为降序）
    }

    // 重写toString方便打印
    @Override
    public String toString() {
        return "Student{name='" + name + "', age=" + age + "}";
    }
}

public class ComparableDemo {
    public static void main(String[] args) {
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student("张三", 20));
        studentList.add(new Student("李四", 18));
        studentList.add(new Student("王五", 22));

        Collections.sort(studentList); // 直接使用默认规则排序
        System.out.println("按年龄升序：");
        studentList.forEach(System.out::println);
        // 输出：
        // Student{name='李四', age=18}
        // Student{name='张三', age=20}
        // Student{name='王五', age=22}
    }
}




//四、自定义对象排序（使用 Comparator 比较器）

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// 普通学生类（无需实现任何接口）
class Student {
    private String name;
    private int age;

    // 构造方法
    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // 提供getter方法（比较器需访问私有属性）
    public String getName() { return name; }
    public int getAge() { return age; }

    // 重写toString方便打印
    @Override
    public String toString() {
        return "Student{name='" + name + "', age=" + age + "}";
    }
}

public class ComparatorDemo {
    public static void main(String[] args) {
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student("张三", 20));
        studentList.add(new Student("李四", 18));
        studentList.add(new Student("王五", 22));

        // 1. 按年龄降序
        Collections.sort(studentList, (s1, s2) -> s2.getAge() - s1.getAge());
        System.out.println("按年龄降序：");
        studentList.forEach(System.out::println);

        // 2. 按姓名字典序
        Collections.sort(studentList, (s1, s2) -> s1.getName().compareTo(s2.getName()));
        System.out.println("\n按姓名排序：");
        studentList.forEach(System.out::println);
    }
}