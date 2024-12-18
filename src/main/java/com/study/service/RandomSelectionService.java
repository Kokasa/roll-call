package com.study.service;

import com.study.entity.Group;
import com.study.entity.Student;

public interface RandomSelectionService {
    Group getRandomGroup();
    Student getRandomStudentFromGroup(int groupId);
    Student getRandomStudentFromClass(int classId);
}
