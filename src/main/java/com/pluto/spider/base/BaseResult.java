package com.pluto.spider.base;

import java.io.Serializable;

/**
 * @author hehaijin
 * @version 1.0
 * @description TODO
 * @date 2018/10/8
 */
public class BaseResult implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 返回状态码 */
    private Integer code;

    /** 返回信息 */
    private String message;

    /** 返回数据 */
    private Object result;

    public BaseResult(){
        super();
    }

    public BaseResult(Integer code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public BaseResult(Integer code, String message, Object result) {
        super();
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
