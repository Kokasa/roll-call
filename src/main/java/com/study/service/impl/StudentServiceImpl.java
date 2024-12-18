package com.study.service.impl;

import com.study.dao.MyClassStudentCSVDao;
import com.study.dao.StudentCSVDao;
import com.study.entity.MyClassStudent;
import com.study.entity.Student;
import com.study.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class StudentServiceImpl implements StudentService {
    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    private final StudentCSVDao studentCSVDao;
    private final MyClassStudentCSVDao myClassStudentCSVDao;

    public StudentServiceImpl(StudentCSVDao studentCSVDao, MyClassStudentCSVDao myClassStudentCSVDao) {
        this.studentCSVDao = studentCSVDao;
        this.myClassStudentCSVDao = myClassStudentCSVDao;
    }

    @Override
    public String addStudent(Student student) {
        if (student == null) {
            logger.error("Cannot add null student");
            return "添加失败：学生对象不能为空";
        }

        if (student.getStudentId() == null) {
            logger.error("Cannot add student with null ID");
            return "添加失败：学生ID不能为空";
        }

        List<Student> students = studentCSVDao.readStudentFromCSV();

        // Check for duplicate student ID
        boolean exists = students.stream()
            .anyMatch(s -> s.getStudentId().equals(student.getStudentId()));

        if (exists) {
            logger.warn("Student with ID {} already exists", student.getStudentId());
            return "添加失败：学生ID已存在";
        }

        students.add(student);
        studentCSVDao.writeStudentToCSV(students);
        logger.info("Student added successfully: {}", student);
        return "添加成功";
    }

    @Override
    public String setClass(MyClassStudent myClassStudent) {
        if (myClassStudent == null) {
            logger.error("Cannot set class for null MyClassStudent");
            return "设置失败：班级学生对象不能为空";
        }

        List<MyClassStudent> myClassStudents = myClassStudentCSVDao.readMyClassStudentFromCSV();

        // Check for duplicate class-student mapping
        boolean exists = myClassStudents.stream()
            .anyMatch(mcs -> mcs.getStudentId().equals(myClassStudent.getStudentId())
                && mcs.getMyClassId().equals(myClassStudent.getMyClassId()));

        if (exists) {
            logger.warn("Student {} already assigned to class {}",
                myClassStudent.getStudentId(), myClassStudent.getMyClassId());
            return "设置失败：学生已在该班级中";
        }

        myClassStudents.add(myClassStudent);
        myClassStudentCSVDao.writeMyClassStudentToCSV(myClassStudents);
        logger.info("Student {} assigned to class {}",
            myClassStudent.getStudentId(), myClassStudent.getMyClassId());
        return "设置成功";
    }

    @Override
    public String updateStudent(Student student) {
        if (student == null || student.getStudentId() == null) {
            logger.error("Cannot update null or student with null ID");
            return "更新失败：学生对象或ID不能为空";
        }

        List<Student> students = studentCSVDao.readStudentFromCSV();
        boolean updated = false;

        for (Student s : students) {
            if (s.getStudentId().equals(student.getStudentId())) {
                s.setStudentName(student.getStudentName());
                updated = true;
                break;
            }
        }

        if (!updated) {
            logger.warn("No student found with ID {}", student.getStudentId());
            return "更新失败：未找到指定学生";
        }

        studentCSVDao.writeStudentToCSV(students);
        logger.info("Student updated successfully: {}", student);
        return "更新成功";
    }

    @Override
    public String deleteStudent(Integer studentId) {
        if (studentId == null) {
            logger.error("Cannot delete student with null ID");
            return "删除失败：学生ID不能为空";
        }

        List<Student> students = studentCSVDao.readStudentFromCSV();
        boolean removed = students.removeIf(s -> s.getStudentId().equals(studentId));

        if (!removed) {
            logger.warn("No student found with ID {}", studentId);
            return "删除失败：未找到指定学生";
        }

        // Also remove student from any class associations
        List<MyClassStudent> myClassStudents = myClassStudentCSVDao.readMyClassStudentFromCSV();
        myClassStudents.removeIf(mcs -> mcs.getStudentId().equals(studentId));
        myClassStudentCSVDao.writeMyClassStudentToCSV(myClassStudents);

        studentCSVDao.writeStudentToCSV(students);
        logger.info("Student deleted successfully: {}", studentId);
        return "删除成功";
    }

    @Override
    public Student getStudent(Integer studentId) {
        if (studentId == null) {
            logger.error("Cannot get student with null ID");
            return null;
        }

        List<Student> students = studentCSVDao.readStudentFromCSV();
        return students.stream()
            .filter(s -> s.getStudentId().equals(studentId))
            .findFirst()
            .orElse(null);
    }

    @Override
    public List<Student> getAllStudent() {
        return studentCSVDao.readStudentFromCSV();
    }

    @Override
    public Student getRandomStudent() {
        List<Student> students = studentCSVDao.readStudentFromCSV();
        return students.isEmpty() ? null :
            students.get((int) (Math.random() * students.size()));
    }
}
