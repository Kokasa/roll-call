package com.study.dao;

import com.study.entity.Group;
import com.study.utils.DaoUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GroupCSVDao {
    private String csvFilePath = "E:\\Workspace\\javaSE\\works\\rool-call\\src\\main\\resources\\group.csv";

    /**
     * 从CSV文件中读取班级信息
     *
     ** @return
     */
    public List<Group> readGroupFromCSV() {
        List<Group> groups = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
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
     * 将小组信息写入CSV文件
     * @param groups
     */
    public void writeGroupToCSV(List<Group> groups) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath))) {
            for (Group group : groups) {
                writer.write(group.getGroupId() + "," + group.getGroupName());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
