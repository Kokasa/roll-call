package com.study.service.impl;

import com.study.dao.GroupCSVDao;
import com.study.dao.GroupStudentCSVDao;
import com.study.dao.MyClassStudentCSVDao;
import com.study.entity.Group;
import com.study.entity.GroupStudent;
import com.study.entity.Student;
import com.study.service.GroupService;
import com.study.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GroupServiceImpl implements GroupService {
    private static final Logger logger = LoggerFactory.getLogger(GroupServiceImpl.class);

    private final GroupCSVDao groupCSVDao;
    private final GroupStudentCSVDao groupStudentCSVDao;
    private final MyClassStudentCSVDao myClassStudentCSVDao;
    private final StudentService studentService;

    public GroupServiceImpl(GroupCSVDao groupCSVDao, MyClassStudentCSVDao myClassStudentCSVDao, StudentService studentService) {
        this.groupCSVDao = groupCSVDao;
        this.myClassStudentCSVDao = myClassStudentCSVDao;
        this.groupStudentCSVDao = new GroupStudentCSVDao();
        this.studentService = studentService;
    }

    @Override
    public String addGroup(Group group) {
        if (group == null) {
            logger.error("Cannot add null group");
            return "添加失败：小组对象不能为空";
        }

        if (group.getGroupId() == null) {
            logger.error("Cannot add group with null ID");
            return "添加失败：小组ID不能为空";
        }

        List<Group> groups = groupCSVDao.readGroupFromCSV();

        // Check for duplicate group ID
        boolean exists = groups.stream()
            .anyMatch(g -> g.getGroupId().equals(group.getGroupId()));

        if (exists) {
            logger.warn("Group with ID {} already exists", group.getGroupId());
            return "添加失败：小组ID已存在";
        }

        groups.add(group);
        groupCSVDao.writeGroupToCSV(groups);
        logger.info("Group added successfully: {}", group);
        return "添加成功";
    }

    @Override
    public List<Student> getAllStudentInGroup(Integer groupId) {
        if (groupId == null) {
            logger.error("Cannot get students for null group ID");
            return new ArrayList<>();
        }

        List<GroupStudent> groupStudents = groupStudentCSVDao.readGroupStudentFromCSV();
        return groupStudents.stream()
            .filter(gs -> gs.getGroupId().equals(groupId))
            .map(gs -> studentService.getStudent(gs.getStudentId()))
            .filter(student -> student != null)
            .collect(Collectors.toList());
    }

    @Override
    public String addStudent(GroupStudent groupStudent) {
        if (groupStudent == null) {
            logger.error("Cannot add null group student");
            return "添加失败：小组学生对象不能为空";
        }

        List<GroupStudent> groupStudents = groupStudentCSVDao.readGroupStudentFromCSV();

        // Check for duplicate group-student mapping
        boolean exists = groupStudents.stream()
            .anyMatch(gs -> gs.getGroupId().equals(groupStudent.getGroupId())
                && gs.getStudentId().equals(groupStudent.getStudentId()));

        if (exists) {
            logger.warn("Student {} already in group {}",
                groupStudent.getStudentId(), groupStudent.getGroupId());
            return "添加失败：学生已在该小组中";
        }

        groupStudents.add(groupStudent);
        groupStudentCSVDao.writeGroupStudentToCSV(groupStudents);
        logger.info("Student {} added to group {}",
            groupStudent.getStudentId(), groupStudent.getGroupId());
        return "添加成功";
    }

    @Override
    public String updateGroup(Group group) {
        if (group == null || group.getGroupId() == null) {
            logger.error("Cannot update null or group with null ID");
            return "更新失败：小组对象或ID不能为空";
        }

        List<Group> groups = groupCSVDao.readGroupFromCSV();
        boolean updated = false;

        for (Group g : groups) {
            if (g.getGroupId().equals(group.getGroupId())) {
                g.setGroupName(group.getGroupName());
                updated = true;
                break;
            }
        }

        if (!updated) {
            logger.warn("No group found with ID {}", group.getGroupId());
            return "更新失败：未找到指定小组";
        }

        groupCSVDao.writeGroupToCSV(groups);
        logger.info("Group updated successfully: {}", group);
        return "更新成功";
    }

    @Override
    public String deleteGroup(Integer groupId) {
        if (groupId == null) {
            logger.error("Cannot delete group with null ID");
            return "删除失败：小组ID不能为空";
        }

        List<Group> groups = groupCSVDao.readGroupFromCSV();
        boolean removed = groups.removeIf(g -> g.getGroupId().equals(groupId));

        if (!removed) {
            logger.warn("No group found with ID {}", groupId);
            return "删除失败：未找到指定小组";
        }

        // Also remove all students from this group
        List<GroupStudent> groupStudents = groupStudentCSVDao.readGroupStudentFromCSV();
        groupStudents.removeIf(gs -> gs.getGroupId().equals(groupId));
        groupStudentCSVDao.writeGroupStudentToCSV(groupStudents);

        groupCSVDao.writeGroupToCSV(groups);
        logger.info("Group deleted successfully: {}", groupId);
        return "删除成功";
    }

    @Override
    public Group getGroup(Integer groupId) {
        if (groupId == null) {
            logger.error("Cannot get group with null ID");
            return null;
        }

        List<Group> groups = groupCSVDao.readGroupFromCSV();
        return groups.stream()
            .filter(g -> g.getGroupId().equals(groupId))
            .findFirst()
            .orElse(null);
    }

    @Override
    public List<Group> getAllGroup() {
        return groupCSVDao.readGroupFromCSV();
    }

    @Override
    public Group getRandomGroup() {
        List<Group> groups = groupCSVDao.readGroupFromCSV();
        return groups.isEmpty() ? null :
            groups.get((int) (Math.random() * groups.size()));
    }
}
