package com.study.entity;

import lombok.Data;

@Data
public class MyClassStudent {
    private Integer myClassId;
    private Integer studentId;

    public MyClassStudent() {
    }
    public MyClassStudent(Integer myClassId, Integer studentId) {
        this.myClassId = myClassId;
        this.studentId = studentId;
    }
}
