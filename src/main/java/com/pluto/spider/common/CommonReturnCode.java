package com.pluto.spider.common;


import com.pluto.spider.base.ReturnCode;

/**
 * @author hehaijin
 * @version 1.0
 * @description TODO
 * @date 2018/10/8
 */
public enum CommonReturnCode implements ReturnCode {


    /** 500服务器出错 */
    INTERNAL_SERVER_ERROR(500, "服务器出错"),
    /**200 成功*/
    SUCCESS(200,"succeed"),
    /** 0 操作失败*/
    FAILED(0,"failed")
    ;


    /**
     * 返回消息
     */
    private Integer code;

    /**
     * 返回状态码
     */
    private String message;

    private CommonReturnCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
