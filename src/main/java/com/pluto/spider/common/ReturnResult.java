package com.pluto.spider.common;


import com.pluto.spider.base.BaseResult;
import com.pluto.spider.base.ReturnCode;

/**
 * @author hehaijin
 * @version 1.0
 * @description TODO
 * @date 2018/10/8
 */
public class ReturnResult extends BaseResult {

    public ReturnResult(){super();}

    public ReturnResult(ReturnCode returnCode){
        super(returnCode.getCode(),returnCode.getMessage());
    }

    public ReturnResult(ReturnCode returnCode,Object result){
        super(returnCode.getCode(),returnCode.getMessage(),result);
    }

    public ReturnResult (Integer code, String message){
        super(code,message);
    }
}
