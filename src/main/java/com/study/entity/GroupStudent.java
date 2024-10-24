package com.study.entity;

import lombok.Data;
@Data
public class GroupStudent {
    private Integer groupId;
    private Integer studentId;

    public GroupStudent() {
    }
    public GroupStudent(Integer groupId, Integer studentId) {
        this.groupId = groupId;
        this.studentId = studentId;
    }
}
