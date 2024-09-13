package com.wc.single.sky.common.enums;

public enum OperatorLogTypeEnum {
    CREATE("Create","Create"),
    EDIT("Edit","Edit"),
    CLOSE("Close","Close"),
    DELETE("Delete","Delete"),
    SAVE("Save","Save"),
    CANCEL("Cancel","Cancel"),
    WITHDRAW("Withdraw","Withdraw"),
    SUBMIT("Submit","Submit"),

    DEACTIVATE("Deactivate","Deactivate"),
    SKIP_APPROVING("Skip Approving","Skip Approving"),
    UPLOAD("Upload","Upload"),
    SPLIT("split","split"),
    ATTACH("Attach","Attach"),
    ON_HOLD("On Hold","On Hold");

    private final String code;
    private final String desc;


    OperatorLogTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
