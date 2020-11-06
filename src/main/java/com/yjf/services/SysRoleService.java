package com.yjf.services;

import com.github.pagehelper.PageInfo;
import com.yjf.entity.SysRole;

import java.util.List;
import java.util.Map;

/**
 * @author 余俊锋
 * @date 2020/11/4 14:30
 * @Description
 */
public interface SysRoleService  extends BaseService<SysRole,Integer>{
     PageInfo<SysRole> selectPage(Integer pageNum, Integer pageSize, Map<String,Object> map);

     int deleteBatch( int roleId,  int[] ids);

     int insertNoRole(Integer roleId,List<Integer> userIds);

     int updateByPrimaryKeySelective(Map<String,Object> map);
}
