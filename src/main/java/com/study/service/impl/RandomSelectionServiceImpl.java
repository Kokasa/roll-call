package com.study.service.impl;

import com.study.dao.RandomSelectionDao;
import com.study.entity.Group;
import com.study.entity.Student;
import com.study.service.RandomSelectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RandomSelectionServiceImpl implements RandomSelectionService {
    private static final Logger logger = LoggerFactory.getLogger(RandomSelectionServiceImpl.class);
    private final RandomSelectionDao randomSelectionDao;

    public RandomSelectionServiceImpl(RandomSelectionDao randomSelectionDao) {
        this.randomSelectionDao = randomSelectionDao;
    }

    @Override
    public Group getRandomGroup() {
        logger.info("Service: Getting random group");
        return randomSelectionDao.getRandomGroup();
    }

    @Override
    public Student getRandomStudentFromGroup(int groupId) {
        logger.info("Service: Getting random student from group: {}", groupId);
        return randomSelectionDao.getRandomStudentFromGroup(groupId);
    }

    @Override
    public Student getRandomStudentFromClass(int classId) {
        logger.info("Service: Getting random student from class: {}", classId);
        return randomSelectionDao.getRandomStudentFromClass(classId);
    }
}
