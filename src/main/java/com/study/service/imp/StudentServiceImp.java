package com.study.service.imp;

import com.study.dao.MyClassStudentCSVDao;
import com.study.dao.StudentCSVDao;
import com.study.entity.MyClassStudent;
import com.study.entity.Student;
import com.study.service.StudentService;

import java.util.List;

public class StudentServiceImp implements StudentService {
    private StudentCSVDao studentCSVDao;
    private MyClassStudentCSVDao myClassStudentCSVDao;

    public StudentServiceImp() {
        this.studentCSVDao = new StudentCSVDao();
        this.myClassStudentCSVDao = new MyClassStudentCSVDao();
    }



    /**
     * 添加学生对象
     *
     * @param student
     */
    @Override
    public String addStudent(Student student) {
        List<Student> students = studentCSVDao.readStudentFromCSV();
        students.add(student);
        studentCSVDao.writeStudentToCSV(students);

        return "添加成功";
    }

    /**
     * 设置班级
     *
     * @param myClassStudent
     */
    @Override
    public String setClass(MyClassStudent myClassStudent) {
        List<MyClassStudent> myClassStudents = myClassStudentCSVDao.readMyClassStudentFromCSV();
        myClassStudents.add(myClassStudent);
        myClassStudentCSVDao.writeMyClassStudentToCSV(myClassStudents);

        return "设置成功";
    }

    /**
     * 更新学生对象
     *
     * @param student
     */
    @Override
    public String updateStudent(Student student) {
        List<Student> students = studentCSVDao.readStudentFromCSV();
        for (Student s : students) {
            if (s.getStudentId().equals(student.getStudentId())) {
                s.setStudentName(student.getStudentName());
                break;
            }
        }

        studentCSVDao.writeStudentToCSV(students);

        return "更新成功";
    }



    /**
     * 根据id删除学生对象
     *
     * @param studentId
     */
    @Override
    public String deleteStudent(Integer studentId) {
        List<Student> students = studentCSVDao.readStudentFromCSV();
        for (Student s : students) {
            if (s.getStudentId().equals(studentId)) {
                students.remove(s);
                break;
            }
        }
        studentCSVDao.writeStudentToCSV(students);
        return "删除成功";
    }

    /**
     * 根据id获取学生对象
     *
     * @param studentId
     * @return
     */
    @Override
    public Student getStudent(Integer studentId) {
        List<Student> students = studentCSVDao.readStudentFromCSV();
        for (Student s : students) {
            if (s.getStudentId().equals(studentId)) {
                return s;
            }
        }
        return null;
    }

    /**
     * 获取所有学生对象
     *
     * @return
     */
    @Override
    public List<Student> getAllStudent() {
        return studentCSVDao.readStudentFromCSV();
    }

    /**
     * 随机获取学生对象
     *
     * @return
     */
    @Override
    public Student getRandomStudent() {
        List<Student> students = studentCSVDao.readStudentFromCSV();
        return students.get((int) (Math.random() * students.size()));
    }
}
