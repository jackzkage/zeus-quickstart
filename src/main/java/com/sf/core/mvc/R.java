package com.sf.core.mvc;

import com.sf.core.constant.ResultStatusEnum;

/**
 * @author zhonglj
 * @date 2017/12/6
 */
public class R<T extends IResponse> extends AbstractResultMsg {

    private R() {
        setSuccess(true);
        setCode(ResultStatusEnum.SUCCESS.getValue());
        setMessage(ResultStatusEnum.SUCCESS.getText());
    }

    private R(String message) {
        setSuccess(true);
        setCode(ResultStatusEnum.SUCCESS.getValue());
        setMessage(message);
    }

    private R(boolean success, String message, String code) {
        setSuccess(success);
        setMessage(message);
        setCode(code);
    }

    public R(T data) {
        setSuccess(true);
        setCode(ResultStatusEnum.SUCCESS.getValue());
        setMessage(ResultStatusEnum.SUCCESS.getText());
        setData(data);
    }

    public static R msg() {
        return new R<>();
    }

    public static R msg(String message) {
        return new R<>(message);
    }

    public static <T extends IResponse> R msg(T data) {
        return new R<>(data);
    }

    public static  R msg(boolean success, String message, String code) {
        return new R<>(success,message,code);
    }

    public static R error(String message) {
        return new R<>(false,message,ResultStatusEnum.SUCCESS.getValue());
    }


    public static R error(String message,String code) {
        return new R<>(false,message,code);
    }

}
