package com.study.dao;

import com.study.entity.Group;
import com.study.entity.Student;

public interface RandomSelectionDao {
    Group getRandomGroup();
    Student getRandomStudentFromGroup(int groupId);
    Student getRandomStudentFromClass(int classId);
}
