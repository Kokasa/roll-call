package com.study.controller;

import com.study.entity.Group;
import com.study.entity.GroupStudent;
import com.study.entity.Student;
import com.study.service.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class GroupController {
    private static final Logger logger = LoggerFactory.getLogger(GroupController.class);
    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    /**
     * 新增小组
     *
     */
    public String addGroup(Group group) {
        logger.info("Controller: Adding group: {}", group);
        return groupService.addGroup(group);
    }

    /**
     * 删除小组
     */
    public String deleteGroup(int groupId) {
        logger.info("Controller: Deleting group: {}", groupId);
        return groupService.deleteGroup(groupId);
    }

    /**
     * 添加学生
     */
    public String addStudent(GroupStudent groupStudent) {
        logger.info("Controller: Adding student to group: {}", groupStudent);
        return groupService.addStudent(groupStudent);
    }

    /**
     * 查询所有小组
     */
    public List<Group> getAllGroup() {
        logger.info("Controller: Getting all groups");
        return groupService.getAllGroup();
    }

    /**
     * 展示小组内所有成员
     */
    public List<Student> getAllStudentInGroup(int groupId) {
        logger.info("Controller: Getting all students in group: {}", groupId);
        return groupService.getAllStudentInGroup(groupId);
    }
}
