package com.study.service;

import com.study.entity.Group;
import com.study.entity.GroupStudent;
import com.study.entity.Student;

import java.util.List;

public interface GroupService {
    /**
     * 添加组对象
     * @param group
     */
    public String addGroup(Group group);

    /**
     * 更新组对象
     * @param group
     */
    public String updateGroup(Group group);


    /**
     * 查询组里所有学生
     */
     public List<Student> getAllStudentInGroup(Integer groupId);

    /**
     * 添加成员
     */
    public String addStudent(GroupStudent groupStudent);

    /**
     * 根据id删除组对象
     * @param groupId
     */
    public String deleteGroup(Integer groupId);

    /**
     * 根据id获取组对象
     * @param groupId
     * @return
     */
    public Group getGroup(Integer groupId);

    /**
     * 获取所有组对象
     * @return
     */
    public List<Group> getAllGroup();

    /**
     * 随机获取组对象
     * @return
     */
    public Group getRandomGroup();
}
