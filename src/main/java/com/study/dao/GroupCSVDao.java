package com.study.dao;

import com.study.entity.Group;
import com.study.entity.GroupStudent;
import com.study.entity.Student;
import com.study.util.FileUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GroupCSVDao {
    private String csvFilePath = "E:\\Workspace\\javaSE\\works\\rool-call\\src\\main\\resources\\group.csv";
    private String groupStudentCsvPath = "E:\\Workspace\\javaSE\\works\\rool-call\\src\\main\\resources\\groupstudent.csv";
    private final StudentCSVDao studentDao;

    public GroupCSVDao() {
        this.studentDao = new StudentCSVDao();
    }

    /**
     * 获取所有小组
     */
    public List<Group> getAllGroup() {
        return readGroupFromCSV();
    }

    /**
     * 获取小组内所有学生
     */
    public List<Student> getAllStudentInGroup(int groupId) {
        List<GroupStudent> groupStudents = readGroupStudentFromCSV();
        List<Integer> studentIds = groupStudents.stream()
            .filter(gs -> gs.getGroupId() == groupId)
            .map(GroupStudent::getStudentId)
            .collect(Collectors.toList());

        List<Student> allStudents = studentDao.readStudentFromCSV();
        return allStudents.stream()
            .filter(student -> studentIds.contains(student.getStudentId()))
            .collect(Collectors.toList());
    }

    /**
     * 从CSV文件中读取小组数据
     */
    public List<Group> readGroupFromCSV() {
        List<Group> groups = new ArrayList<>();
        try (BufferedReader reader = FileUtils.createReader(csvFilePath)) {
            String row;
            while ((row = reader.readLine()) != null) {
                String[] data = row.split(",");
                Group group = new Group(Integer.parseInt(data[0]), data[1]);
                groups.add(group);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return groups;
    }

    /**
     * 读取小组学生关系
     */
    private List<GroupStudent> readGroupStudentFromCSV() {
        List<GroupStudent> groupStudents = new ArrayList<>();
        try (BufferedReader reader = FileUtils.createReader(groupStudentCsvPath)) {
            String row;
            while ((row = reader.readLine()) != null) {
                String[] data = row.split(",");
                GroupStudent groupStudent = new GroupStudent();
                groupStudent.setGroupId(Integer.parseInt(data[0]));
                groupStudent.setStudentId(Integer.parseInt(data[1]));
                groupStudents.add(groupStudent);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return groupStudents;
    }

    /**
     * 将小组信息写入CSV文件
     */
    public void writeGroupToCSV(List<Group> groups) {
        try (BufferedWriter writer = FileUtils.createWriter(csvFilePath)) {
            for (Group group : groups) {
                writer.write(group.getGroupId() + "," + group.getGroupName());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
