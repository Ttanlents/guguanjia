package com.yjf.services;

import com.github.pagehelper.PageInfo;
import com.yjf.entity.SysOffice;

import java.util.List;

/**
 * @author 余俊锋
 * @date 2020/10/27 17:05
 * @Description
 */
public interface SysOfficeService extends BaseService<SysOffice,Integer> {
    PageInfo<SysOffice> selectPage(Integer pageNum,Integer pageSize,SysOffice sysOffice);


    List<SysOffice> selectByRoleId(Integer roleId);
}
