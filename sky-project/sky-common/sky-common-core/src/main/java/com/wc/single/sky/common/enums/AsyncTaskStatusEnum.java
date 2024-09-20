package com.wc.single.sky.common.enums;

import lombok.Getter;

/**
 * 异步任务状态
 *
 * @author qiaodf2
 */

@Getter
public enum AsyncTaskStatusEnum {
    /**
     * 0进行中1完成-1出错
     */

    complete(1, "Success"),
    error(-1, "Fail"),
    inProgress(3, "In Progress"),
    notStart(4, "Not Start"),
    cancel(5, "Cancelled"),
    cancelling(6, "Cancelling"),
    ;

    AsyncTaskStatusEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    private final int code;
    private final String name;

    public static String getName(int code) {
        for (AsyncTaskStatusEnum value : values()) {
            if (value.code == code) {
                return value.name;
            }
        }
        return null;
    }
}
