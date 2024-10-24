package com.study.controller;

import com.study.entity.Group;
import com.study.entity.GroupStudent;
import com.study.service.GroupService;

import java.util.Scanner;

public class GroupController {
    private final GroupService groupService;
    private final Scanner scanner;
    public GroupController(GroupService groupService, Scanner scanner) {
        this.groupService = groupService;
        this.scanner = scanner;
    }

    /**
     * 新增小组
     */
    public void addGroup() {
        Group group = new Group();

        System.out.println("请输入小组编号: ");
        group.setGroupId(scanner.nextInt());
        System.out.println("请输入小组名称: ");
        group.setGroupName(scanner.next());

        System.out.println(groupService.addGroup(group));

        getAllGroup();
    }


    /**
     * 删除小组
     */
    public void deleteGroup() {
        System.out.println("请输入小组编号: ");
        System.out.println(groupService.deleteGroup(scanner.nextInt()));

        getAllGroup();
    }

    /**
     * 修改小组
     */
    public void updateGroup() {
        Group group = new Group();

        System.out.println("您要修改哪个小组, 请输入小组编号: ");
        group.setGroupId(scanner.nextInt());
        System.out.println("请输入新的小组名称: ");
        group.setGroupName(scanner.next());

        System.out.println(groupService.updateGroup(group));

        getAllGroup();
    }

    /**
     * 展示小组内所有成员
     */
    public void getAllStudentInGroup() {
        System.out.println("请输入小组编号: ");
        System.out.println(groupService.getAllStudentInGroup(scanner.nextInt()));
    }

    /**
     * 添加学生
     */
    public void addStudent() {
        GroupStudent groupStudent = new GroupStudent();
        System.out.println("请输入小组编号: ");
        groupStudent.setGroupId(scanner.nextInt());
        System.out.println("请输入学生编号: ");
        groupStudent.setStudentId(scanner.nextInt());
        System.out.println(groupService.addStudent(groupStudent));

        getAllStudentInGroup();
    }

    /**
     * 按照id查询小组
     */
    public void getGroupById() {
        System.out.println("请输入小组编号: ");
        System.out.println(groupService.getGroup(scanner.nextInt()));
    }

    /**
     * 查询随机小组
     */
    public void getRandomGroup() {
        System.out.println(groupService.getRandomGroup());
    }

    /**
     * 查询所有小组
     */
    public void getAllGroup() {
        for (Group group : groupService.getAllGroup()) {
            System.out.println(group);
        }
    }
}
