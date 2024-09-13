package com.wc.single.sky.system.enums;

/**
 * * 操作类型
 */
public enum SystemLogTypeEnum {


    /*
     * 业务操作日志
     * */
    SYS_BUSINESS_OPERATOR_LOG("sysBusinessOperatorLog", "sysBusinessOperatorLog"),
    SYS_BUSINESS_MASSUPLOAD_LOG("sysBusinessMassUploadLog", "sysBusinessMassUploadLog"),
    SYS_OPER_LOG("sysOperLog", "sysOperLog"),
    SYS_KAFKA_LOG("sysKafkaLog", "sysKafkaLog"),
    SYS_LOGIN_IN_FOR("sysLogininfor", "sysLogininfor"),

    ;


    SystemLogTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    private final String code;
    private final String name;

    public String getCode() {
        return code;
    }

    public static SystemLogTypeEnum getEnumByCode(String code) {
        for (SystemLogTypeEnum systemLogTypeEnum : SystemLogTypeEnum.values()) {
            if (systemLogTypeEnum.getCode().equals(code)) {
                return systemLogTypeEnum;
            }
        }
        return null;
    }

}
