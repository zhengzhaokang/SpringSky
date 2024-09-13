package com.wc.single.sky.common.redis.common;

public class LockType {

    public static final String REENTRANT_LOCK = "REENTRANT_LOCK";
    public static final String FAIR_LOCK = "FAIR_LOCK";
    public static final String READ_LOCK = "READ_LOCK";
    public static final String WRITE_LOCK = "WRITE_LOCK";

    private LockType() {
    }
}
