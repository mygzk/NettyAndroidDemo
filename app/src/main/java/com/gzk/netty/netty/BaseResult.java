package com.gzk.netty.netty;

/**
 * 解析数据基类
 * @param <T>
 */
public class BaseResult<T> {
    /**
     * 响应吗
     */
    private int code;
    /**
     * 错误信息
     */
    private String errMsg;
    /**
     * 类型
     */
    private int type;
    /**
     * 具体数据
     */
    private T data;


    public int getCode() {
        return code;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public int getType() {
        return type;
    }

    public T getData() {
        return data;
    }
}
