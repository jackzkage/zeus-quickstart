package com.sf.mapper;

import com.sf.core.mybatis.mapper.MyMapper;
import com.sf.model.SysUser;

public interface SysUserMapper extends MyMapper<SysUser> {

    public SysUser queryByStaffId(String staffId);

}