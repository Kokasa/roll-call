package com.study.service.impl;

import com.study.dao.MyClassCSVDao;
import com.study.dao.MyClassStudentCSVDao;
import com.study.dao.StudentCSVDao;
import com.study.entity.MyClass;
import com.study.entity.MyClassStudent;
import com.study.entity.Student;
import com.study.service.MyClassService;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Collectors;

public class MyClassServiceImpl implements MyClassService {
    private static final Logger logger = LoggerFactory.getLogger(MyClassServiceImpl.class);

    private final MyClassCSVDao myClassCSVDao;
    private final MyClassStudentCSVDao myClassStudentCSVDao;
    private final StudentCSVDao studentCSVDao;

    public MyClassServiceImpl(MyClassCSVDao myClassCSVDao, MyClassStudentCSVDao myClassStudentCSVDao, StudentCSVDao studentCSVDao) {
        this.myClassCSVDao = myClassCSVDao;
        this.myClassStudentCSVDao = myClassStudentCSVDao;
        this.studentCSVDao = studentCSVDao;
    }

    @Override
    public String addClass(MyClass myClass) {
        if (myClass == null) {
            logger.error("Cannot add null class");
            return "添加失败：班级对象不能为空";
        }

        if (myClass.getMyClassId() == null) {
            logger.error("Cannot add class with null ID");
            return "添加失败：班级ID不能为空";
        }

        List<MyClass> classes = myClassCSVDao.readClassFromCSV();

        // Check for duplicate class ID
        boolean exists = classes.stream()
            .anyMatch(c -> c.getMyClassId().equals(myClass.getMyClassId()));

        if (exists) {
            logger.warn("Class with ID {} already exists", myClass.getMyClassId());
            return "添加失败：班级ID已存在";
        }

        classes.add(myClass);
        myClassCSVDao.writeClassToCSV(classes);
        logger.info("Class added successfully: {}", myClass);
        return "添加成功";
    }

    @Override
    public List<Student> getAllStudentInClass(Integer classId) {
        if (classId == null) {
            logger.error("Cannot get students for null class ID");
            return new ArrayList<>();
        }

        List<MyClassStudent> myClassStudents = myClassStudentCSVDao.readMyClassStudentFromCSV();
        List<Student> allStudents = studentCSVDao.readStudentFromCSV();

        return myClassStudents.stream()
            .filter(mcs -> mcs.getMyClassId().equals(classId))
            .map(mcs -> {
                Integer studentId = mcs.getStudentId();
                return allStudents.stream()
                    .filter(s -> s.getStudentId().equals(studentId))
                    .findFirst()
                    .orElse(null);
            })
            .filter(student -> student != null)
            .collect(Collectors.toList());
    }

    @Override
    public String updateClass(MyClass myClass) {
        if (myClass == null || myClass.getMyClassId() == null) {
            logger.error("Cannot update null or class with null ID");
            return "更新失败：班级对象或ID不能为空";
        }

        List<MyClass> classes = myClassCSVDao.readClassFromCSV();
        boolean updated = false;

        for (MyClass c : classes) {
            if (c.getMyClassId().equals(myClass.getMyClassId())) {
                c.setMyClassName(myClass.getMyClassName());
                updated = true;
                break;
            }
        }

        if (!updated) {
            logger.warn("No class found with ID {}", myClass.getMyClassId());
            return "更新失败：未找到指定班级";
        }

        myClassCSVDao.writeClassToCSV(classes);
        logger.info("Class updated successfully: {}", myClass);
        return "更新成功";
    }

    @Override
    public String deleteClass(Integer classId) {
        if (classId == null) {
            logger.error("Cannot delete class with null ID");
            return "删除失败：班级ID不能为空";
        }

        List<MyClass> classes = myClassCSVDao.readClassFromCSV();
        boolean removed = classes.removeIf(c -> c.getMyClassId().equals(classId));

        if (!removed) {
            logger.warn("No class found with ID {}", classId);
            return "删除失败：未找到指定班级";
        }

        // Also remove all students from this class
        List<MyClassStudent> myClassStudents = myClassStudentCSVDao.readMyClassStudentFromCSV();
        myClassStudents.removeIf(mcs -> mcs.getMyClassId().equals(classId));
        myClassStudentCSVDao.writeMyClassStudentToCSV(myClassStudents);

        myClassCSVDao.writeClassToCSV(classes);
        logger.info("Class deleted successfully: {}", classId);
        return "删除成功";
    }

    @Override
    public MyClass getClass(Integer classId) {
        if (classId == null) {
            logger.error("Cannot get class with null ID");
            return null;
        }

        List<MyClass> classes = myClassCSVDao.readClassFromCSV();
        return classes.stream()
            .filter(c -> c.getMyClassId().equals(classId))
            .findFirst()
            .orElse(null);
    }

    @Override
    public List<MyClass> getAllClass() {
        return myClassCSVDao.readClassFromCSV();
    }

    @Override
    public MyClass getRandomClass() {
        List<MyClass> classes = myClassCSVDao.readClassFromCSV();
        return classes.isEmpty() ? null :
            classes.get((int) (Math.random() * classes.size()));
    }

    @Override
    public String addStudentToClass(Integer classId, Integer studentId) {
        // 验证班级是否存在
        List<MyClass> classes = myClassCSVDao.readClassFromCSV();
        boolean classExists = classes.stream()
                .anyMatch(c -> c.getMyClassId().equals(classId));
        if (!classExists) {
            return "班级不存在";
        }

        // 验证学生是否存在
        List<Student> students = studentCSVDao.readStudentFromCSV();
        boolean studentExists = students.stream()
                .anyMatch(s -> s.getStudentId().equals(studentId));
        if (!studentExists) {
            return "学生不存在";
        }

        // 检查学生是否已在班级中
        List<MyClassStudent> myClassStudents = myClassStudentCSVDao.readMyClassStudentFromCSV();
        boolean alreadyInClass = myClassStudents.stream()
                .anyMatch(mcs -> mcs.getMyClassId().equals(classId) &&
                                mcs.getStudentId().equals(studentId));
        if (alreadyInClass) {
            return "该学生已在班级中";
        }

        // 添加学生到班级
        MyClassStudent myClassStudent = new MyClassStudent(classId, studentId);
        myClassStudents.add(myClassStudent);
        myClassStudentCSVDao.writeMyClassStudentToCSV(myClassStudents);
        return "添加成功";
    }

    @Override
    public String removeStudentFromClass(Integer classId, Integer studentId) {
        List<MyClassStudent> myClassStudents = myClassStudentCSVDao.readMyClassStudentFromCSV();
        boolean removed = myClassStudents.removeIf(mcs ->
            mcs.getMyClassId().equals(classId) && mcs.getStudentId().equals(studentId));

        if (!removed) {
            return "该学生不在班级中";
        }

        myClassStudentCSVDao.writeMyClassStudentToCSV(myClassStudents);
        return "删除成功";
    }
}
