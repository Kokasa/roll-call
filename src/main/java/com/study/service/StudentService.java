package com.study.service;

import com.study.entity.MyClassStudent;
import com.study.entity.Student;
import java.util.List;

public interface StudentService {
    /**
     * 添加学生对象
     *
     * @param student 学生对象
     * @return 操作结果
     */
    String addStudent(Student student);

    /**
     * 设置班级
     */
    String setClass(MyClassStudent myClassStudent);


    /**
     * 更新学生对象
     *
     * @param student 学生对象
     * @return 操作结果
     */
    String updateStudent(Student student);

    /**
     * 根据id删除学生对象
     *
     * @param studentId 学生ID
     * @return 操作结果
     */
    String deleteStudent(Integer studentId);

    /**
     * 根据id获取学生对象
     *
     * @param studentId 学生ID
     * @return 学生对象
     */
    Student getStudent(Integer studentId);

    /**
     * 获取所有学生对象
     *
     * @return 学生对象列表
     */
    List<Student> getAllStudent();

    /**
     * 随机获取学生对象
     *
     * @return 学生对象
     */
    Student getRandomStudent();
}
