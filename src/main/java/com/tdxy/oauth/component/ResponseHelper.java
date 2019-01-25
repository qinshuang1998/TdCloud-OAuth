package com.tdxy.oauth.component;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 请求统一响应格式
 *
 * @param <T>
 * @author Qug_
 */
@SuppressWarnings("unchecked")
public class ResponseHelper<T> {
    @JSONField(ordinal = 1)
    private Boolean result;

    @JSONField(ordinal = 2)
    private Integer code = 0;

    @JSONField(ordinal = 3)
    private String msg = "";

    @JSONField(ordinal = 4)
    private T data = null;

    public ResponseHelper<T> sendSuccess() {
        this.result = true;
        this.code = 200;
        return this;
    }

    public ResponseHelper<T> sendSuccess(T data) {
        this.result = true;
        this.code = 200;
        this.data = data;
        return this;
    }

    public ResponseHelper<T> sendSuccess(T data, String msg) {
        this.result = true;
        this.code = 200;
        this.data = data;
        this.msg = msg;
        return this;
    }

    public ResponseHelper<T> sendError() {
        this.result = false;
        this.code = 500;
        return this;
    }

    public ResponseHelper<T> sendError(String msg) {
        this.result = false;
        this.code = 500;
        this.msg = msg;
        return this;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
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
