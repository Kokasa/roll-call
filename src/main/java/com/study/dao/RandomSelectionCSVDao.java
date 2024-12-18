package com.study.dao;

import com.study.entity.Group;
import com.study.entity.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Random;

public class RandomSelectionCSVDao implements RandomSelectionDao {
    private static final Logger logger = LoggerFactory.getLogger(RandomSelectionCSVDao.class);
    private final GroupCSVDao groupDao;
    private final MyClassStudentCSVDao classStudentDao;
    private final Random random;

    public RandomSelectionCSVDao(GroupCSVDao groupDao, MyClassStudentCSVDao classStudentDao) {
        this.groupDao = groupDao;
        this.classStudentDao = classStudentDao;
        this.random = new Random();
    }

    @Override
    public Group getRandomGroup() {
        logger.info("DAO: Getting random group");
        List<Group> groups = groupDao.getAllGroup();
        if (groups.isEmpty()) {
            logger.warn("No groups found");
            return null;
        }
        Group selectedGroup = groups.get(random.nextInt(groups.size()));
        logger.info("Selected random group: {}", selectedGroup);
        return selectedGroup;
    }

    @Override
    public Student getRandomStudentFromGroup(int groupId) {
        logger.info("DAO: Getting random student from group: {}", groupId);
        List<Student> students = groupDao.getAllStudentInGroup(groupId);
        if (students.isEmpty()) {
            logger.warn("No students found in group: {}", groupId);
            return null;
        }
        Student selectedStudent = students.get(random.nextInt(students.size()));
        logger.info("Selected random student from group {}: {}", groupId, selectedStudent);
        return selectedStudent;
    }

    @Override
    public Student getRandomStudentFromClass(int classId) {
        logger.info("DAO: Getting random student from class: {}", classId);
        List<Student> students = classStudentDao.getAllStudentInClass(classId);
        if (students.isEmpty()) {
            logger.warn("No students found in class: {}", classId);
            return null;
        }
        Student selectedStudent = students.get(random.nextInt(students.size()));
        logger.info("Selected random student from class {}: {}", classId, selectedStudent);
        return selectedStudent;
    }
}
