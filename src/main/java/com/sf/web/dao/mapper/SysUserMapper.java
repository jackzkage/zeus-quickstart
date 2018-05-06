package com.sf.web.dao.mapper;

import com.sf.core.mybatis.mapper.MyMapper;
import com.sf.web.dao.model.SysUser;

/**
 * @author lijie.zh
 */
public interface SysUserMapper extends MyMapper<SysUser> {

    SysUser queryByStaffId(String staffId);

}