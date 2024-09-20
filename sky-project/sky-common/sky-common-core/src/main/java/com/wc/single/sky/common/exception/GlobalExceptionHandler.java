package com.wc.single.sky.common.exception;

import com.wc.single.sky.common.core.domain.R;
import com.wc.single.sky.common.exception.message.DefaultErrorMessage;
import com.wc.single.sky.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理器
 *
 * @Author lovefamily
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 请求方式不支持
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)
    public R handleException(HttpRequestMethodNotSupportedException e) {
        logger.error(e.getMessage(), e);
        return R.error(DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.REQUEST_METHOD_IS_NOT_SUPPORTED, "") + " " + e.getMethod());
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public R notFount(RuntimeException e) {
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }
        logger.error("运行时异常:", e);
        return R.error(e.getMessage());
    }

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(QdpException.class)
    public R handleWindException(QdpException e) {
        String result= StringUtils.isNotEmpty(e.getMsg())?e.getMsg():e.toString();
        return R.error(e.getCode(), e.getMessage(),result);
    }


    @ExceptionHandler(DuplicateKeyException.class)
    public R handleDuplicateKeyException(DuplicateKeyException e) {
        logger.error(e.getMessage(), e);
        return R.error(DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.DB_RECORD_REPEAT, ""));
    }

    @ExceptionHandler(Exception.class)
    public R handleException(Exception e) {
        logger.error(e.getMessage(), e);
        return R.error(DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.SERVER_ERROR, ""),e.toString());
    }

    /**
     * 捕获并处理未授权异常
     *
     * @param e 授权异常
     * @return 统一封装的结果类, 含有代码code和提示信息msg
     */
    @ExceptionHandler(UnauthorizedException.class)
    public R handle401(UnauthorizedException e) {
        return R.error(401, e.getMessage());
    }

    // 验证码错误
    @ExceptionHandler(ValidateCodeException.class)
    public R handleCaptcha(ValidateCodeException e) {
        return R.error(e.getMessage());
    }
}