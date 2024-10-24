package com.study.dao;

import com.study.entity.MyClass;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MyClassCSVDao {
    private String csvFilePath = "E:\\Workspace\\javaSE\\works\\rool-call\\src\\main\\resources\\myclass.csv";

    /**
     * 从CSV文件中读取班级信息
     * @return 班级列表
     */
    public List<MyClass> readClassFromCSV() {
        List<MyClass> classes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            String row;
            while ((row = reader.readLine()) != null) {
                String[] data = row.split(",");
                MyClass myClass = new MyClass(Integer.parseInt(data[0]), data[1]);
                classes.add(myClass);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classes;
    }

    /**
     * 将班级信息写入CSV文件
     * @param classes 班级列表
     */
    public void writeClassToCSV(List<MyClass> classes) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath))) {
            for (MyClass myClass : classes) {
                writer.write(myClass.getMyClassId() + "," + myClass.getMyClassName());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}