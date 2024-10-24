package com.study.entity;

import lombok.Data;

@Data
public class Group {
    private Integer groupId;
    private String groupName;

    public Group(Integer groupId, String groupName) {
        this.groupId = groupId;
        this.groupName = groupName;
    }

    public Group() {

    }
}
