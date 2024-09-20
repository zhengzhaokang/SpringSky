package com.wc.single.sky.common.exception.user;

import com.wc.single.sky.common.exception.base.BaseException;

/**
 * 用户信息异常类
 * 
 * @author lovefamily
 */
public class UserException extends BaseException
{
    private static final long serialVersionUID = 1L;

    public UserException(String code, Object[] args)
    {
        super("user", code, args, null);
    }

    public UserException(String code, Object[] args, String defaultMessage) {
        super("user", code, null, defaultMessage);
    }

    @Override
    public String getMessage()
    {
        return getDefaultMessage();
    }
}
