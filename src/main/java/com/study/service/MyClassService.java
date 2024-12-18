package com.study.service;

import com.study.entity.MyClass;
import com.study.entity.Student;

import java.util.List;

public interface MyClassService {
    /**
     * 添加班级对象
     *
     * @param myClass 班级对象
     * @return 操作结果
     */
    public String addClass(MyClass myClass);

    /**
     * 查询班级内所有学生
     *
     * @param classId 班级ID
     * @return 操作结果
     */
    public List<Student> getAllStudentInClass(Integer classId);

    /**
     * 向班级添加学生
     * @param classId 班级ID
     * @param studentId 学生ID
     * @return 操作结果提示信息
     */
    String addStudentToClass(Integer classId, Integer studentId);

    /**
     * 从班级中删除学生
     * @param classId 班级ID
     * @param studentId 学生ID
     * @return 操作结果提示信息
     */
    String removeStudentFromClass(Integer classId, Integer studentId);

    /**
     * 更新班级对象
     *
     * @param myClass 班级对象
     * @return 操作结果
     */
    String updateClass(MyClass myClass);

    /**
     * 根据id删除班级对象
     *
     * @param classId 班级ID
     * @return 操作结果
     */
    String deleteClass(Integer classId);

    /**
     * 根据id获取班级对象
     *
     * @param classId 班级ID
     * @return 班级对象
     */
    MyClass getClass(Integer classId);

    /**
     * 获取所有班级对象
     *
     * @return 班级对象列表
     */
    List<MyClass> getAllClass();

    /**
     * 随机获取班级对象
     *
     * @return 班级对象
     */
    MyClass getRandomClass();
}
