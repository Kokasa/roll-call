package com.study.controller;

import com.study.entity.Group;
import com.study.entity.Student;
import com.study.service.RandomSelectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RandomSelectionController {
    private static final Logger logger = LoggerFactory.getLogger(RandomSelectionController.class);
    private final RandomSelectionService randomSelectionService;

    public RandomSelectionController(RandomSelectionService randomSelectionService) {
        this.randomSelectionService = randomSelectionService;
    }

    public Group getRandomGroup() {
        logger.info("Controller: Getting random group");
        return randomSelectionService.getRandomGroup();
    }

    public Student getRandomStudentFromGroup(int groupId) {
        logger.info("Controller: Getting random student from group: {}", groupId);
        return randomSelectionService.getRandomStudentFromGroup(groupId);
    }

    public Student getRandomStudentFromClass(int classId) {
        logger.info("Controller: Getting random student from class: {}", classId);
        return randomSelectionService.getRandomStudentFromClass(classId);
    }
}
