package com.study.controller;

import com.study.entity.MyClass;
import com.study.service.MyClassService;

import java.util.Scanner;

public class MyClassController {
    private final MyClassService myClassService;
    private final Scanner scanner;
    public MyClassController(MyClassService myClassService, Scanner scanner) {
        this.myClassService = myClassService;
        this.scanner = scanner;
    }

    /**
     * 新增班级
     */
    public void addClass() {
        MyClass myClass = new MyClass();

        System.out.println("请输入班级编号: ");
        myClass.setMyClassId(scanner.nextInt());
        System.out.println("请输入班级名称: ");
        myClass.setMyClassName(scanner.next());

        System.out.println(myClassService.addClass(myClass));

        getAllClass();
    }

    /**
     * 删除班级
     */
    public void deleteClass() {
        System.out.println("请输入班级编号: ");
        System.out.println(myClassService.deleteClass(scanner.nextInt()));

        getAllClass();
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
    public void getAllStudentInClass() {
        System.out.println("请输入班级编号: ");
        System.out.println(myClassService.getAllStudentInClass(scanner.nextInt()));
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
    public void getAllClass() {
        for (MyClass myClass : myClassService.getAllClass()) {
            System.out.println(myClass);
        }
    }
}
