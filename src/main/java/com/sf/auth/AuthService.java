package com.sf.auth;

import com.sf.auth.model.CurrentUser;
import com.sf.auth.model.AuthDO;
import com.sf.core.exception.BusinessException;
import com.sf.mapper.SysUserMapper;
import com.sf.model.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 衷立杰
 * @date 2018/3/27
 */
@Service
@Slf4j
public class AuthService {

    @Autowired
    SysUserMapper sysUserMapper;

    public CurrentUser login(String loginName, String password) {
        SysUser sysUser = sysUserMapper.queryByStaffId(loginName);
        if (null == sysUser) {
            throw new BusinessException("用户不存在");
        }
        //TODO password要进行加密
        if (!password.equals(sysUser.getPassword())) {
            throw new BusinessException("密码错误");
        }

        CurrentUser currentUser = CurrentUser.builder()
                .userId(sysUser.getUserId())
                .mobileNo(sysUser.getMobileNo())
                .userName(sysUser.getUserName())
                .avatar(sysUser.getAvatar())
                .sex(sysUser.getSex())
                .lastLoginIp(sysUser.getLastLoginIp())
                .lastLoginTime(sysUser.getLastLoginTime()).build();

        return currentUser;
    }

}
