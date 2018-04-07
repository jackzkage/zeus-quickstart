package com.sf.core.exception;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author zhonglj
 * @date 2017/12/6
 */
@Getter
@Setter
public class BusinessException extends RuntimeException {

    String errCode;
    String errMsg;

    public BusinessException(String errMsg){
        setErrMsg(errMsg);
    }

    public BusinessException(String errMsg, String errCode){
        setErrCode(errCode);
        setErrMsg(errMsg);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }
}
