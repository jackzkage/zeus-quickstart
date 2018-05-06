package com.sf.web.dao.model;

import lombok.Data;

import javax.persistence.*;

/**
 * 基础信息
 *
 * @author lijie.zh
 */
@Data
public class BaseEntity {

    @Transient
    private Integer page = 1;

    @Transient
    private Integer rows = 10;


}