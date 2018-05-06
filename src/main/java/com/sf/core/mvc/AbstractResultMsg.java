package com.sf.core.mvc;

import lombok.Data;

import java.io.Serializable;


/**
 * @param <T>
 * @author lijie.zhong
 */
@Data
public abstract class AbstractResultMsg<T> implements Serializable {

    protected String code;

    protected String message;

    protected Boolean success = Boolean.TRUE;

    protected T data;

}