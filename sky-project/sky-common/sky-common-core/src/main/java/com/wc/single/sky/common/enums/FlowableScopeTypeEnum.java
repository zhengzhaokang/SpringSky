package com.wc.single.sky.common.enums;

public enum FlowableScopeTypeEnum {

    ADD_AFTER_ASSIGNEE("Add_After_Assignee"),
    GRANT_ASSIGNEE("Grant_Assignee");

    private String type;

    FlowableScopeTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
