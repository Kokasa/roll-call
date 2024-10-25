package com.study.service.imp;

import com.study.dao.GroupCSVDao;
import com.study.dao.GroupStudentCSVDao;
import com.study.entity.Group;
import com.study.entity.GroupStudent;
import com.study.entity.Student;
import com.study.service.GroupService;
import com.study.service.StudentService;

import java.util.ArrayList;
import java.util.List;

public class GroupServiceImp implements GroupService {
    private GroupCSVDao groupCSVDao;

    private GroupStudentCSVDao groupStudentCSVDao;

    private StudentService studentService;

    public GroupServiceImp() {
        groupCSVDao = new GroupCSVDao();
        groupStudentCSVDao = new GroupStudentCSVDao();
        studentService = new StudentServiceImp();
    }
    /**
     * 添加组对象
     *
     * @param group
     */
    @Override
    public String addGroup(Group group) {
        List<Group> groups = groupCSVDao.readGroupFromCSV();
        groups.add(group);
        groupCSVDao.writeGroupToCSV(groups);

        return "添加成功";
    }

    /**
     * 查询组内所有学生
     *
     * @param groupId
     * @return 操作结果
     */
    @Override
    public List<Student> getAllStudentInGroup(Integer groupId) {
        List<Student> students = new ArrayList<>();
        List<GroupStudent> groupStudents = groupStudentCSVDao.readGroupStudentFromCSV();
        for (GroupStudent groupStudent : groupStudents) {
            if (groupStudent.getGroupId().equals(groupId)) {
                students.add(studentService.getStudent(groupStudent.getStudentId()));
            }

        }
        return students;
    }
    /**
     * 添加学生
     */
    @Override
    public String addStudent(GroupStudent groupStudent) {
        List<GroupStudent> groupStudents = groupStudentCSVDao.readGroupStudentFromCSV();
        groupStudents.add(groupStudent);
        groupStudentCSVDao.writeGroupStudentToCSV(groupStudents);


        return "添加成功";
    }

    /**
     * 更新组对象
     *
     * @param group
     */
    @Override
    public String updateGroup(Group group) {
        List<Group> groups = groupCSVDao.readGroupFromCSV();
        for (Group g : groups) {
            if (g.getGroupId().equals(group.getGroupId())) {
                g.setGroupName(group.getGroupName());
                break;
            }
        }

        groupCSVDao.writeGroupToCSV(groups);

        return "更新成功";
    }

    /**
     * 根据id删除组对象
     *
     * @param groupId
     */
    @Override
    public String deleteGroup(Integer groupId) {
        List<Group> groups = groupCSVDao.readGroupFromCSV();
        for (Group g : groups) {
            if (g.getGroupId().equals(groupId)) {
                groups.remove(g);
                break;
            }
        }
        groupCSVDao.writeGroupToCSV(groups);
        return "删除成功";
    }

    /**
     * 根据id获取组对象
     *
     * @param groupId
     * @return
     */
    @Override
    public Group getGroup(Integer groupId) {
        List<Group> groups = groupCSVDao.readGroupFromCSV();
        for (Group g : groups) {
            if (g.getGroupId().equals(groupId)) {
                return g;
            }
        }
        return null;
    }

    /**
     * 获取所有组对象
     *
     * @return
     */
    @Override
    public List<Group> getAllGroup() {
        return groupCSVDao.readGroupFromCSV();
    }

    /**
     * 随机获取组对象
     *
     * @return
     */
    @Override
    public Group getRandomGroup() {
        List<Group> groups = groupCSVDao.readGroupFromCSV();
        return groups.get((int) (Math.random() * groups.size()));
    }
}
