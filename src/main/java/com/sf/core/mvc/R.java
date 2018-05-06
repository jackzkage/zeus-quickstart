package com.sf.core.mvc;

import com.sf.core.constant.ResultStatusEnum;

/**
 * @param <T>
 * @author lijie.zh
 */
public final class R<T extends IResponse> extends AbstractResultMsg {

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

    private <T extends IResponse> R(T data) {
        setSuccess(true);
        setCode(ResultStatusEnum.SUCCESS.getValue());
        setMessage(ResultStatusEnum.SUCCESS.getText());
        setData(data);
    }

    public static R success() {
        return new R<>();
    }

    public static R success(String message) {
        return new R<>(message);
    }

    public static R success(String message, String code) {
        return new R<>(true, message, code);
    }

    public static <T extends IResponse> R<T> success(T data) {
        return new R<T>(data);
    }

    public static R error(String message) {
        return new R<>(false, message, ResultStatusEnum.SUCCESS.getValue());
    }

    public static R error(String message, String code) {
        return new R<>(false, message, code);
    }

}
