package com.sf.core.mvc;

import com.sf.core.exception.InvalidParameterException;

/**
 * @author lijie.zh
 */
public interface IRequest {

    /**
     * 自定义的参数校验方法
     *
     * @throws InvalidParameterException
     */
    void check() throws InvalidParameterException;

}
