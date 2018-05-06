package com.sf.core.exception;

import lombok.Getter;
import lombok.Setter;


/**
 * @author lijie.zh
 */
@Getter
@Setter
public class InvalidParameterException extends BusinessException {


    public InvalidParameterException(String errMsg) {
        super(errMsg);
    }

    public InvalidParameterException(String errMsg, String errCode) {
        super(errCode, errMsg);
    }

    public InvalidParameterException(Throwable cause) {
        super(cause);
    }
}
