package com.yjf.services;

import com.github.pagehelper.PageInfo;
import com.yjf.entity.SysUser;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author 余俊锋
 * @date 2020/10/26 15:55
 * @Description
 */
public interface SysUserService  extends BaseService<SysUser,Integer> {
   PageInfo<SysUser> selectPage(Integer pageNum,Integer pageSize,SysUser sysUser);

   List<SysUser> selectRole(SysUser sysUser);

   List<SysUser> selectNoRole(Integer officeId,  Integer roleId);

   int insertSelective(SysUser sysUser, HttpSession session);

   int updateByPrimaryKeySelective(SysUser sysUser,HttpSession session);
}
