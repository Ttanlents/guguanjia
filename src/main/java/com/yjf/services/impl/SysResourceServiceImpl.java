package com.yjf.services.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yjf.dao.SysResourceDao;
import com.yjf.entity.SysResource;
import com.yjf.services.SysResourceService;
import com.yjf.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    @Override
    //@CacheEvict(value="users", allEntries=true)
    public int updateByParentId(Map<String,Object> map){
        int result=0;
        String oldParentIds;
        if (map.containsKey("menu")&&!StringUtils.isEmpty(map.get("menu"))){
            String json = JsonUtils.pojoToJson(map.get("menu"));
            SysResource sysResource = JsonUtils.jsonToPojo(json, SysResource.class);
            sysResource.setUpdateDate(new Date());
            result+= sysResourceDao.updateByPrimaryKeySelective(sysResource); //更新自己
            if (map.containsKey("oldParentIds")&&!StringUtils.isEmpty(map.get("oldParentIds"))){
                 oldParentIds = (String)map.get("oldParentIds");
                String parentIds = sysResource.getParentIds();
                if (!Objects.equals(oldParentIds,parentIds)){
                    Integer parentId = sysResource.getId();   //更新子集列表
                    result+=sysResourceDao.updateByParentId(oldParentIds,parentIds,parentId.toString());
                }
            }
        }
        return  result;

    }


}
