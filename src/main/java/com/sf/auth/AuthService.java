package com.sf.auth;

import com.sf.auth.model.CurrentUser;
import com.sf.core.exception.BusinessException;
import com.sf.util.IPUtils;
import com.sf.web.dao.mapper.SysUserMapper;
import com.sf.web.dao.model.SysUser;
import com.sf.web.service.PasswordEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

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

        if (!PasswordEncoder.validPassword(password, sysUser.getPassword())) {
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


    /**
     * 登录操作之后记录信息
     *
     * @param userId
     * @param request
     */

    public void updateInfoAfterLogin(Long userId, HttpServletRequest request) {
        String ip = IPUtils.getClientIpAddress(request);
        SysUser user = new SysUser();
        user.setLastLoginIp(ip);
        user.setLastLoginTime(new Date());
        user.setUserId(userId);
        sysUserMapper.updateByPrimaryKeySelective(user);
    }

}
