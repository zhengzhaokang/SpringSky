package com.wc.single.sky.common.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum FlowActionEnum {
    Withdraw("Withdraw","撤回"),
    Save("Save","保存"),
    Submit("Submit","提交"),
    APPROVE("Approve","批准"),
    REJECT("Reject","拒绝"),
    ADD_AFTER_APPROVER("Add after-Approver","加签"),
    DECREASE_APPROVER("Decrease approver","减签");

    private String code;
    private String message;

    private FlowActionEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static Map<String, String> getMap() {
        Map<String, String> map = new HashMap<>();
        for (FlowActionEnum i : values()) {
            map.put(i.getCode() + "", i.getMessage());
        }
        return map;
    }

    public static List<Map<String, String>> getAllinfo() {
        List<Map<String, String>> list = new ArrayList<>();
        for (FlowActionEnum item : FlowActionEnum.values()) {
            Map<String, String> map = new HashMap<>();
            map.put("code", item.getCode() + "");
            map.put("message", item.getMessage());
            list.add(map);
        }
        return list;
    }

    public String getCode() {
        return code;
    }


    public String getMessage() {
        return message;
    }

}
