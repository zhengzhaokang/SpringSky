package com.wc.single.sky.common.constant;

public class LuaStrConstants {

    public static final String SCRIPT_REDIS_CALL_INCRBYFLOAT_KEYS = " redis.call('incrbyfloat',KEYS[";
    public static final String SCRIPT_NOT_REDIS_CALL_GET_KEYS = "not(redis.call('get',KEYS[";
    public static final String SCRIPT_AND = "])) and ";
    public static final String ARGV = "],ARGV[";
}
