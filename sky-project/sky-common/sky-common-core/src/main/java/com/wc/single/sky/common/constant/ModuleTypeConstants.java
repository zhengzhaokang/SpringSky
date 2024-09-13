package com.wc.single.sky.common.constant;

import cn.hutool.core.util.StrUtil;

import java.util.HashSet;
import java.util.Set;

public class ModuleTypeConstants {

    public static final String PAYMENT = "Payment";
    public static final String BUDGET = "Budget";
    public static final String PROGRAM = "Program";
    public static final String ACCRUAL = "Accrual";
    public static final String BANLANCE = "BalanceAdjustment";
    public static final String CRITICAL = "Crirical";
    public static final String CLAIM = "Claim";

    private static final Set<String> SET = new HashSet<>();

    static {
        SET.add(PAYMENT);
        SET.add(BUDGET);
        SET.add(PROGRAM);
        SET.add(ACCRUAL);
        SET.add(BANLANCE);
        SET.add(CRITICAL);
        SET.add(CLAIM);
    }

    public static final boolean has(String module) {
        if (StrUtil.isBlank(module)) {
            return Boolean.FALSE;
        }
        return SET.contains(module);
    }

}
