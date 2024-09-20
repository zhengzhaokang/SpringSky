package com.wc.single.sky.common.core.domain;

import com.wc.single.sky.common.constant.HttpStatus;

import java.io.Serializable;

/**
 * @author guowb1
 * @date 2022/5/24 16:42
 */
public class ResultDTO<T> implements Serializable {

    private static final int SUCCESS_CODE = HttpStatus.SUCCESS;
    private static final int FAIL_CODE = HttpStatus.ERROR;
    private static final String SUCCESS_MSG = "success";
    private static final String FAIL_MSG = "fail";

    private final Integer code;
    private final boolean success;
    private final String msg;
    private final T data;
    private final String result;

    public ResultDTO() {
        this.code = FAIL_CODE;
        this.success = false;
        this.msg = FAIL_MSG;
        this.data = null;
        this.result=FAIL_MSG;
    }

    public ResultDTO(int code, boolean success, String msg, T data) {
        this.code = code;
        this.success = success;
        this.msg = msg;
        this.data = data;
        result = msg;
    }

    public ResultDTO(int code, boolean success, String msg, T data,String result) {
        this.code = code;
        this.success = success;
        this.msg = msg;
        this.data = data;
        this.result=result;
    }

    public String getResult() {
        return result;
    }

    public int getCode() {
        return code;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public static <T> ResultDTO<T> success(){
        return new ResultDTO<>(SUCCESS_CODE, true, SUCCESS_MSG, null);
    }

    /**
     * 返回错误消息
     *
     * @return
     */
    public static <T> ResultDTO<T> error()
    {
        return new ResultDTO<>(FAIL_CODE, true, FAIL_MSG, null);
    }

    public static <T> ResultDTO<T> success(T data) {
        return new ResultDTO<>(SUCCESS_CODE, true, SUCCESS_MSG, data);
    }

    public static <T> ResultDTO<T> success(T data, String msg) {
        return new ResultDTO<>(SUCCESS_CODE, true, msg, data);
    }

    public static <T> ResultDTO<T> fail() {
        return new ResultDTO<>();
    }

    public static <T> ResultDTO<T> fail(String msg) {
        return new ResultDTO<>(FAIL_CODE, false, msg, null);
    }

    public static <T> ResultDTO<T> fail(T data) {
        return new ResultDTO<>(FAIL_CODE, false, FAIL_MSG, data);
    }

    public static <T> ResultDTO<T> fail(Integer code, String msg) {
        return new ResultDTO<>(code == null ? FAIL_CODE : code, false, msg, null);
    }
    public static <T> ResultDTO<T> fail(Integer code, String msg, T data) {
        return new ResultDTO<>(code == null ? FAIL_CODE : code, false, msg, data);
    }
     public static <T> ResultDTO<T> fail(Integer code, String msg, T data,String result) {
        return new ResultDTO<>(code == null ? FAIL_CODE : code, false, msg, data,result);
    }
}
