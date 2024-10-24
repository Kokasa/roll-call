package com.study.dao;

import com.study.entity.GroupStudent;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GroupStudentCSVDao {
    private String csvFilePath = "E:\\Workspace\\javaSE\\works\\rool-call\\src\\main\\resources\\groupstudent.csv";

    /**
     * 从CSV文件中读取班级信息
     * @return 班级列表
     */
    public List<GroupStudent> readGroupStudentFromCSV() {
        List<GroupStudent> groupStudents = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            String row;
            while ((row = reader.readLine()) != null) {
                String[] data = row.split(",");
                GroupStudent myClass = new GroupStudent(Integer.parseInt(data[0]), Integer.parseInt(data[1]));
                groupStudents.add(myClass);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return groupStudents;
    }

    /**
     * 将班级信息写入CSV文件
     * @param groupStudents 班级列表
     */
    public void writeGroupStudentToCSV(List<GroupStudent> groupStudents) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath))) {
            for (GroupStudent groupStudent : groupStudents) {
                writer.write(groupStudent.getGroupId() + "," + groupStudent.getStudentId());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

