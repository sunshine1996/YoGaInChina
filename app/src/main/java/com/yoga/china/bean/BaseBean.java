package com.yoga.china.bean;

/**
 * 基类bean
 * Created by sunsiyuan on 16/2/19.
 */
public class BaseBean<T> {

    private int code;
    private T data;
    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
