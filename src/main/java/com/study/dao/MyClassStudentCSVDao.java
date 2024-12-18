package com.study.dao;

import com.study.entity.MyClassStudent;
import com.study.entity.Student;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MyClassStudentCSVDao {
    private String csvFilePath = "E:\\Workspace\\javaSE\\works\\rool-call\\src\\main\\resources\\myclassstudent.csv";
    private final StudentCSVDao studentDao;

    public MyClassStudentCSVDao() {
        this.studentDao = new StudentCSVDao();
    }

    /**
     * 获取班级中的所有学生
     */
    public List<Student> getAllStudentInClass(int classId) {
        List<MyClassStudent> classStudents = readMyClassStudentFromCSV();
        List<Integer> studentIds = classStudents.stream()
            .filter(cs -> cs.getMyClassId() == classId)
            .map(MyClassStudent::getStudentId)
            .collect(Collectors.toList());
        
        List<Student> allStudents = studentDao.readStudentFromCSV();
        return allStudents.stream()
            .filter(student -> studentIds.contains(student.getStudentId()))
            .collect(Collectors.toList());
    }

    /**
     * 从CSV文件中读取班级学生关系数据
     */
    public List<MyClassStudent> readMyClassStudentFromCSV() {
        List<MyClassStudent> myClassStudents = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            String row;
            while ((row = reader.readLine()) != null) {
                String[] data = row.split(",");
                MyClassStudent myClass = new MyClassStudent(Integer.parseInt(data[0]), Integer.parseInt(data[1]));
                myClassStudents.add(myClass);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myClassStudents;
    }

    /**
     * 将班级学生关系写入CSV文件
     */
    public void writeMyClassStudentToCSV(List<MyClassStudent> myClassStudents) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath))) {
            for (MyClassStudent myClassStudent : myClassStudents) {
                writer.write(myClassStudent.getMyClassId() + "," + myClassStudent.getStudentId());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
