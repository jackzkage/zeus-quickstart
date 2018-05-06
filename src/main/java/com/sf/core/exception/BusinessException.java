package com.sf.core.exception;

import lombok.Getter;
import lombok.Setter;


/**
 * @author lijie.zh
 */
@Getter
@Setter
public class BusinessException extends RuntimeException {

    String errCode;
    String errMsg;

    public BusinessException(String errMsg) {
        setErrMsg(errMsg);
    }

    public BusinessException(String errMsg, String errCode) {
        setErrCode(errCode);
        setErrMsg(errMsg);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }
}
