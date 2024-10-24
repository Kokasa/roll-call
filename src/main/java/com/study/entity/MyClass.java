package com.study.entity;

import lombok.Data;

@Data
public class MyClass {
    private Integer myClassId;
    private String myClassName;

    public MyClass() {
    }

    public MyClass(Integer myClassId, String myClassName) {
        this.myClassId = myClassId;
        this.myClassName = myClassName;
    }
}
