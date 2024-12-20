package com.study;

import com.study.controller.*;
import com.study.dao.*;
import com.study.service.*;
import com.study.service.impl.GroupServiceImpl;
import com.study.service.impl.MyClassServiceImpl;
import com.study.service.impl.RandomSelectionServiceImpl;
import com.study.service.impl.StudentServiceImpl;
import com.study.views.MainGUI;

import javax.swing.*;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 设置文件编码
        System.setProperty("file.encoding", "UTF-8");
        System.setProperty("sun.jnu.encoding", "UTF-8");

        // 设置控制台输出编码
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 确保Swing组件使用UTF-8
        System.setProperty("sun.nio.cs.map", "x-windows-950/UTF-8");

        // 初始化DAO层
        StudentCSVDao studentDao = new StudentCSVDao();
        GroupCSVDao groupDao = new GroupCSVDao();
        MyClassCSVDao myClassDao = new MyClassCSVDao();
        MyClassStudentCSVDao myClassStudentDao = new MyClassStudentCSVDao();
        RandomSelectionCSVDao randomSelectionDao = new RandomSelectionCSVDao(groupDao, myClassStudentDao);

        // 初始化Service层
        StudentService studentService = new StudentServiceImpl(studentDao, myClassStudentDao);
        GroupService groupService = new GroupServiceImpl(groupDao, myClassStudentDao, studentService);
        MyClassService myClassService = new MyClassServiceImpl(myClassDao, myClassStudentDao, studentDao);
        RandomSelectionService randomSelectionService = new RandomSelectionServiceImpl(randomSelectionDao);

        // 创建Scanner对象
        Scanner scanner = new Scanner(System.in);

        // 初始化Controller层
        StudentController studentController = new StudentController(studentService, scanner);
        GroupController groupController = new GroupController(groupService);
        MyClassController myClassController = new MyClassController(myClassService);
        RandomSelectionController randomSelectionController = new RandomSelectionController(randomSelectionService);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 启动GUI
        SwingUtilities.invokeLater(() -> {
            MainGUI mainGUI = new MainGUI(
                groupController,
                myClassController,
                randomSelectionController
            );
            mainGUI.setVisible(true);
        });

        // 关闭Scanner
        scanner.close();
    }
}
