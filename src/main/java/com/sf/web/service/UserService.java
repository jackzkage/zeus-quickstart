package com.sf.web.service;

import com.sf.web.dao.mapper.SysUserMapper;
import com.sf.web.dao.model.SysUser;
import com.zeus.idworker.generator.IdWorkerInstance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lijie.zh
 */
@Service
@Slf4j
public class UserService {

    @Autowired
    SysUserMapper sysUserMapper;

    public void createUser(SysUser user) {
        user.setUserId(IdWorkerInstance.getId());
        sysUserMapper.insert(user);

    }

}
