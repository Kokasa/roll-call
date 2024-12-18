package com.study.controller;

import com.study.entity.MyClassStudent;
import com.study.entity.Student;
import com.study.service.StudentService;

import java.util.List;
import java.util.Scanner;

public class StudentController {
    private final StudentService studentService;
    private final Scanner scanner;

    public StudentController(StudentService studentService, Scanner scanner) {
        this.studentService = studentService;
        this.scanner = scanner;
    }


    /**
     * 新增学生
     */
    public void addStudent() {
        Student student = new Student();

        System.out.println("请输入学生编号: ");
        student.setStudentId(scanner.nextInt());
        System.out.println("请输入学生姓名: ");
        student.setStudentName(scanner.next());

        System.out.println(studentService.addStudent(student));

        getAllStudent();
    }

    /**
     * 删除学生
     */
    public void deleteStudent() {
        System.out.println("请输入学生编号: ");
        System.out.println(studentService.deleteStudent(scanner.nextInt()));

        getAllStudent();
    }

    /**
     * 修改学生
     */
    public void updateStudent() {
        Student student = new Student();

        System.out.println("您要修改哪个学生, 请输入学生编号: ");
        student.setStudentId(scanner.nextInt());
        System.out.println("请输入新的学生姓名: ");
        student.setStudentName(scanner.next());

        System.out.println(studentService.updateStudent(student));

        getAllStudent();
    }

    /**
     * 设置学生班级
     */
    public void setStudentClass() {
        MyClassStudent myClassStudent = new MyClassStudent();
        System.out.println("请输入学生编号: ");
        myClassStudent.setStudentId(scanner.nextInt());
        System.out.println("请输入班级编号: ");
        myClassStudent.setMyClassId(scanner.nextInt());

        System.out.println(studentService.setClass(myClassStudent));
    }
    /**
     * 按照id查询学生
     */
    public void getStudentById() {
        System.out.println("请输入学生编号: ");
        System.out.println(studentService.getStudent(scanner.nextInt()));
    }

    /**
     * 查询随机学生
     */
    public Student getRandomStudent() {
        return studentService.getRandomStudent();
    }

    /**
     * 查询所有学生
     */
    public List<Student> getAllStudent() {
        for (Student student : studentService.getAllStudent()) {
            System.out.println(student);
        }
        return studentService.getAllStudent();
    }
}
