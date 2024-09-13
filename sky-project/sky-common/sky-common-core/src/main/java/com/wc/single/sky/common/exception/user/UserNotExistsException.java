package com.wc.single.sky.common.exception.user;

/**
 * 用户不存在异常类
 * 
 * @author lovefamily
 */
public class UserNotExistsException extends UserException
{
    private static final long serialVersionUID = 1L;

    public UserNotExistsException()
    {
        super("user.not.exists", null);
    }

    public UserNotExistsException(String message)
    {
        super("user.not.exists", null, message);
    }
}
