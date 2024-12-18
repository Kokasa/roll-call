package com.study.dao;

import com.study.entity.Student;
import com.study.util.FileUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StudentCSVDao {
    private String csvFilePath = "E:\\Workspace\\javaSE\\works\\rool-call\\src\\main\\resources\\student.csv";

    /**
     * 从CSV文件中读取学生信息
     * @return 学生列表
     */
    public List<Student> readStudentFromCSV() {
        List<Student> students = new ArrayList<>();
        try (BufferedReader reader = FileUtils.createReader(csvFilePath)) {
            String row;
            while ((row = reader.readLine()) != null) {
                String[] data = row.split(",");
                Student student = new Student(Integer.parseInt(data[0]), data[1]);
                students.add(student);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return students;
    }

    /**
     * 将学生信息写入CSV文件
     * @param students 学生列表
     */
    public void writeStudentToCSV(List<Student> students) {
        try (BufferedWriter writer = FileUtils.createWriter(csvFilePath)) {
            for (Student student : students) {
                writer.write(student.getStudentId() + "," + student.getStudentName());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}