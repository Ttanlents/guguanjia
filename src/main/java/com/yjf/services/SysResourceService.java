package com.yjf.services;

import com.github.pagehelper.PageInfo;
import com.yjf.entity.SysResource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 余俊锋
 * @date 2020/11/5 14:08
 * @Description
 */
public interface SysResourceService extends BaseService<SysResource,Integer> {
  List<SysResource> SelectByRoleId(Integer roleId);
  List<SysResource>  SelectByUserId(@Param("userId") Integer userId);
  List<SysResource>  selectCommonResources();

  PageInfo<SysResource> selectPage(Integer pageNum,Integer pageSize,String name);
}
