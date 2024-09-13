package com.wc.single.sky.common.core.page;

import java.io.Serializable;
import java.util.List;

/**
 * 表格分页数据对象
 * 
 * @author lovefamily
 */
public class RemoteResponse<T> implements Serializable
{
    /** 总记录数 */
    private long total;
    /** 列表数据 */
    private List<T> rows;
    /** 消息状态码 */
    private int code;

    private T data;

    private String msg;

    /**
     * 表格数据对象
     */
    public RemoteResponse()
    {
    }

    /**
     * 分页
     *
     * @param list 列表数据
     * @param total 总记录数
     */
    public RemoteResponse(List<T> list, int total)
    {
        this.rows = list;
        this.total = total;
    }

    public long getTotal()
    {
        return total;
    }

    public void setTotal(long total)
    {
        this.total = total;
    }

    public List<T> getRows()
    {
        return rows;
    }

    public void setRows(List<T> rows)
    {
        this.rows = rows;
    }

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
