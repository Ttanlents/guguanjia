package com.yjf.services.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yjf.dao.SysUserDao;
import com.yjf.entity.SysUser;
import com.yjf.services.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 余俊锋
 * @date 2020/10/26 15:56
 * @Description
 */
@Transactional
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUser, Integer> implements SysUserService {
    @Autowired
    SysUserDao sysUserDao;

    @Override
    public PageInfo<SysUser> selectPage(Integer pageNum, Integer pageSize, SysUser sysUser) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysUser> sysUserList = sysUserDao.selectPage(sysUser);
        PageInfo<SysUser> pageInfo = new PageInfo<>(sysUserList);
        return pageInfo;
    }

    @Override
    public List<SysUser> selectRole(SysUser sysUser) {
     return    sysUserDao.selectRole(sysUser);
    }

    @Override
    public List<SysUser> selectNoRole(Integer officeId, Integer roleId) {
      return   sysUserDao.selectNoRole(officeId,roleId);
    }
}
