package com.study.controller;

import com.study.entity.MyClass;
import com.study.entity.Student;
import com.study.service.MyClassService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Scanner;

public class MyClassController {
    private static final Logger logger = LoggerFactory.getLogger(MyClassController.class);
    private final MyClassService myClassService;
    private final Scanner scanner = new Scanner(System.in);

    public MyClassController(MyClassService myClassService) {
        this.myClassService = myClassService;
    }

    /**
     * 新增班级
     */
    public String addClass(MyClass myClass) {
        logger.info("Controller: Adding class: {}", myClass);
        return myClassService.addClass(myClass);
    }

    /**
     * 删除班级
     */
    public String deleteClass(int classId) {
        logger.info("Controller: Deleting class: {}", classId);
        return myClassService.deleteClass(classId);
    }

    /**
     * 修改班级
     */
    public void updateClass() {
        MyClass myClass = new MyClass();

        System.out.println("您要修改哪个班级, 请输入班级编号: ");
        myClass.setMyClassId(scanner.nextInt());
        System.out.println("请输入新的班级名称: ");
        myClass.setMyClassName(scanner.next());

        System.out.println(myClassService.updateClass(myClass));

        getAllClass();
    }

    /**
     * 展示班级内学生列表
     */
    public List<Student> getAllStudentInClass(Integer classId) {
        return myClassService.getAllStudentInClass(classId);
    }

    /**
     * 按照id查询班级
     */
    public void getClassById() {
        System.out.println("请输入班级编号: ");
        System.out.println(myClassService.getClass(scanner.nextInt()));
    }

    /**
     * 查询随机班级
     */
    public void getRandomClass() {
        System.out.println(myClassService.getRandomClass());
    }

    /**
     * 查询所有班级
     */
    public List<MyClass> getAllClass() {
        logger.info("Controller: Getting all classes");
        List<MyClass> myClasses = myClassService.getAllClass();
        for (MyClass myClass : myClasses) {
            System.out.println(myClass);
        }
        return myClasses;
    }

    /**
     * 向班级添加学生
     */
    public String addStudentToClass(Integer classId, Integer studentId) {
        return myClassService.addStudentToClass(classId, studentId);
    }

    /**
     * 从班级中删除学生
     */
    public String removeStudentFromClass(Integer classId, Integer studentId) {
        return myClassService.removeStudentFromClass(classId, studentId);
    }
}
