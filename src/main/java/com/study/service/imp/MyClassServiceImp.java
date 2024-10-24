package com.study.service.imp;

import com.study.dao.MyClassCSVDao;
import com.study.dao.MyClassStudentCSVDao;
import com.study.entity.MyClass;
import com.study.entity.MyClassStudent;
import com.study.entity.Student;
import com.study.service.MyClassService;
import com.study.service.StudentService;

import java.util.ArrayList;
import java.util.List;

public class MyClassServiceImp implements MyClassService {
    private MyClassCSVDao myClassCSVDao;
    private MyClassStudentCSVDao myClassStudentCSVDao;

    private StudentService studentService;

    public MyClassServiceImp() {
        myClassCSVDao = new MyClassCSVDao();
        myClassStudentCSVDao = new MyClassStudentCSVDao();
        studentService = new StudentServiceImp();
    }


    /**
     * 添加班级对象
     *
     * @param myClass
     */
    @Override
    public String addClass(MyClass myClass) {
        List<MyClass> classes = myClassCSVDao.readClassFromCSV();
        classes.add(myClass);
        myClassCSVDao.writeClassToCSV(classes);

        return "添加成功";
    }

    /**
     * 查询班级内所有学生
     *
     * @param classId 班级ID
     * @return 操作结果
     */
    @Override
    public List<Student> getAllStudentInClass(Integer classId) {
        List<Student> students = new ArrayList<>();
        List<MyClassStudent> myClassStudents = myClassStudentCSVDao.readMyClassStudentFromCSV();
        for (MyClassStudent myClassStudent : myClassStudents) {
            if (myClassStudent.getMyClassId().equals(classId)) {
                students.add(studentService.getStudent(myClassStudent.getStudentId()));
            }

        }
        return students;
    }


    /**
     * 更新班级对象
     *
     * @param myClass
     */
    @Override
    public String updateClass(MyClass myClass) {
        List<MyClass> classes = myClassCSVDao.readClassFromCSV();
        for (MyClass c : classes) {
            if (c.getMyClassId().equals(myClass.getMyClassId())) {
                c.setMyClassName(myClass.getMyClassName());
                break;
            }
        }

        myClassCSVDao.writeClassToCSV(classes);

        return "更新成功";
    }

    /**
     * 根据id删除班级对象
     *
     * @param classId
     */
    @Override
    public String deleteClass(Integer classId) {
        List<MyClass> classes = myClassCSVDao.readClassFromCSV();
        for (MyClass c : classes) {
            if (c.getMyClassId().equals(classId)) {
                classes.remove(c);
                break;
            }
        }
        myClassCSVDao.writeClassToCSV(classes);
        return "删除成功";
    }

    /**
     * 根据id获取班级对象
     *
     * @param classId
     * @return
     */
    @Override
    public MyClass getClass(Integer classId) {
        List<MyClass> classes = myClassCSVDao.readClassFromCSV();
        for (MyClass c : classes) {
            if (c.getMyClassId().equals(classId)) {
                return c;
            }
        }
        return null;
    }

    /**
     * 获取所有班级对象
     *
     * @return
     */
    @Override
    public List<MyClass> getAllClass() {
        return myClassCSVDao.readClassFromCSV();
    }

    /**
     * 随机获取班级对象
     *
     * @return
     */
    @Override
    public MyClass getRandomClass() {
        List<MyClass> classes = myClassCSVDao.readClassFromCSV();
        return classes.get((int) (Math.random() * classes.size()));
    }
}
