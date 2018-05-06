package com.sf.web.dao.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


/**
 * @author lijie.zh
 */
@Setter
@Getter
@Table(name = "tf_sys_user")
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