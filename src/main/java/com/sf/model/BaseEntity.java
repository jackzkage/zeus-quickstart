package com.sf.model;

import lombok.Data;

import javax.persistence.*;

/**
 * 基础信息
 *
 * @author liuzh
 * @since 2016-01-31 21:42
 */
@Data
public class BaseEntity {

    @Transient
    private Integer page = 1;

    @Transient
    private Integer rows = 10;


}