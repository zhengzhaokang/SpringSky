package com.wc.single.sky.system.util;

import com.wc.single.sky.system.domain.SysUser;
import com.wc.single.sky.common.utils.security.ShaUtils;

public class PasswordUtil {
    public static boolean matches(SysUser user, String newPassword) {
        return user.getPassword().equalsIgnoreCase(encryptPassword(user.getLoginName(), newPassword, user.getSalt()));
    }

    public static String encryptPassword(String username, String password, String salt) {
        return ShaUtils.encodeSHA256(username + password + salt);
    }
}