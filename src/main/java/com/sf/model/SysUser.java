package com.sf.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.Table;
import java.time.DateTimeException;
import java.util.Date;

/**
 * @author liuzh_3nofxnp
 * @since 2016-01-22 22:16
 */
@Setter
@Getter
@Table(name="tf_sys_user")
public class SysUser extends BaseEntity {

    @Id
    private Long userId;

    private String userName;

    private String password;

    private String mobileNo;

    private String staffId;

    private String email;

    private String avatar;

    private Integer sex;

    private Date lastLoginTime;

    private String lastLoginIp;

    private Integer validTag;

    private Date createTime;

    private Date updateTime;

}