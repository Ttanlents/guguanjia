package com.yjf.services.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yjf.dao.SysUserDao;
import com.yjf.entity.SysUser;
import com.yjf.services.SysUserService;
import com.yjf.utils.EncryptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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

    @Override
    public int insertSelective(SysUser sysUser, HttpSession session) {
        int i=0;
        sysUser.setCompanyId(sysUser.getOfficeId());
        sysUser.setCreateDate(new Date());
        sysUser.setUpdateDate(new Date());
        SysUser loginUser=(SysUser)session.getAttribute("loginUser");
        if (loginUser!=null){
            sysUser.setCreateBy(loginUser.getName());
            sysUser.setUpdateBy(loginUser.getName());
        }
        sysUser.setDelFlag("0");
        if (sysUser.getOfficeId()!=null){
            sysUser.setCompanyId(sysUser.getOfficeId());
        }
        if (Objects.equals(sysUser.getRoleId(),"1")){
            sysUser.setUserType("1");
        }else {
            sysUser.setUserType("0");
        }
        sysUser.setPassword(EncryptUtils.MD5_HEX(EncryptUtils.MD5_HEX(sysUser.getPassword())+sysUser.getUsername()));
        i+= super.insertSelective(sysUser);
        i+=sysUserDao.insertUserRole(sysUser.getId(),sysUser.getRoleId(),loginUser.getName());
        return i;
    }

    @Override
    public int updateByPrimaryKeySelective(SysUser sysUser,HttpSession session) {
        int i=0;
        sysUser.setUpdateDate(new Date());
        SysUser loginUser = (SysUser)session.getAttribute("loginUser");
        if (loginUser!=null){
            sysUser.setUpdateBy(loginUser.getName());
        }
        i+= super.updateByPrimaryKeySelective(sysUser);
        i+=sysUserDao.updateRoleByUserId(sysUser.getId(),sysUser.getRoleId());
        return i;
    }
}
