package com.sf.core.mvc;

import com.sf.core.exception.InvalidParameterException;

/**
 *
 * @author zhonglj
 * @date 2017/12/6
 */
public interface IRequest{

    /**
     * 自定义的参数校验方法
     * @throws InvalidParameterException
     */
    public void check() throws InvalidParameterException;

}
