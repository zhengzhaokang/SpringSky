package com.wc.single.sky.common.exception.user;

/**
 * 用户账号已被删除
 * 
 * @author lovefamily
 */
public class UserDeleteException extends UserException
{
    private static final long serialVersionUID = 1L;

    public UserDeleteException()
    {
        super("user.password.delete", null);
    }

    public UserDeleteException(String message)
    {
        super("user.password.delete", null, message);
    }
}
