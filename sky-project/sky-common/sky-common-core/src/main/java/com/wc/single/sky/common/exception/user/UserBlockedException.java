package com.wc.single.sky.common.exception.user;

/**
 * 用户锁定异常类
 * 
 * @author lovefamily
 */
public class UserBlockedException extends UserException
{
    private static final long serialVersionUID = 1L;

    public UserBlockedException()
    {
        super("user.blocked", null);
    }

    public UserBlockedException(String message)
    {
        super("user.blocked", null, message);
    }
}
