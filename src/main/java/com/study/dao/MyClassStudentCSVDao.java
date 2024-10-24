package com.study.dao;

import com.study.entity.MyClassStudent;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MyClassStudentCSVDao {
    private String csvFilePath = "E:\\Workspace\\javaSE\\works\\rool-call\\src\\main\\resources\\myclassstudent.csv";

    /**
     * 从CSV文件中读取班级信息
     * @return 班级列表
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
     * 将班级信息写入CSV文件
     * @param myClassStudents 班级列表
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
