package com.yjf.services.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yjf.dao.SysResourceDao;
import com.yjf.entity.SysResource;
import com.yjf.services.SysResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 余俊锋
 * @date 2020/11/5 14:09
 * @Description
 */
@Service
@Transactional
public class SysResourceServiceImpl extends BaseServiceImpl<SysResource,Integer> implements SysResourceService {
    @Autowired
    SysResourceDao sysResourceDao;
    @Override
    public List<SysResource> SelectByRoleId(Integer roleId) {
        return sysResourceDao.SelectByRoleId( roleId);
    }

    @Override
    public List<SysResource> SelectByUserId(Integer userId) {
        return sysResourceDao.selectByUserId(userId);
    }

    @Override
    @Cacheable(cacheNames = "sysOfficeCache",key = "'com.yjf.services.impl.SysResourceServiceImpl:selectCommonResources'")
    public List<SysResource> selectCommonResources() {
        return sysResourceDao.selectCommonResources();
    }

    @Override
    public PageInfo<SysResource> selectPage(Integer pageNum, Integer pageSize,String name) {
        PageHelper.startPage(pageNum,pageSize);
        List<SysResource> sysResources = sysResourceDao.selectPage(name);
        PageInfo<SysResource> pageInfo = new PageInfo<>(sysResources);
        return pageInfo;
    }



}
