package com.yjf.services.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yjf.dao.SysRoleDao;
import com.yjf.entity.SysRole;
import com.yjf.services.SysRoleService;
import com.yjf.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 余俊锋
 * @date 2020/11/4 14:30
 * @Description
 */
@Service
@Transactional
public class SysRoleServiceImpl extends BaseServiceImpl<SysRole,Integer> implements SysRoleService {
    @Autowired
    SysRoleDao sysRoleDao;
    @Override
    public PageInfo<SysRole> selectPage(Integer pageNum, Integer pageSize, Map<String,Object> map) {
        PageHelper.startPage(pageNum,pageSize);
        List<SysRole> sysRoles = sysRoleDao.selectPage(map);
        PageInfo<SysRole> pageInfo = new PageInfo<>(sysRoles);
        return pageInfo;
    }

    @Override
    public int deleteBatch(int roleId, int[] ids) {
      return   sysRoleDao.deleteBatch(roleId,ids);
    }

    @Override
    public int insertNoRole(Integer roleId, List<Integer> userIds) {
        return sysRoleDao.insertBatch(roleId,userIds);
    }

    @Override
    public int updateByPrimaryKeySelective(Map<String,Object> map) {
        Object role = map.get("role");
        String json = JsonUtils.pojoToJson(role);
        SysRole sysRole = JsonUtils.jsonToPojo(json, SysRole.class);
        sysRole.setUpdateDate(new Date());
        int i = super.updateByPrimaryKeySelective(sysRole);
        List<Integer> resources=null;
        if (map.containsKey("resources")){
            resources=( List<Integer>) map.get("resources");
        }
        List<Integer> _resources=null;
        if (map.containsKey("_resources")){
            _resources=( List<Integer>) map.get("_resources");
        }

        if(resources.size()==_resources.size()&&resources.containsAll(_resources)){//完全一致
            System.out.println("数据一致，不更新权限");
        }else{
            sysRoleDao.deleteResourcesByRoleId(sysRole.getId());
            sysRoleDao.insertRoleResources(sysRole.getId(),resources);
        }

        List<Integer> office = null;
        if(map.containsKey("offices")){
            office = (List<Integer>) map.get("offices");
        }
        List<Integer> _office = null;
        if(map.containsKey("_offices")){
            _office = (List<Integer>) map.get("_offices");
        }
        if(office==null&&_office==null){
            //不更新
            System.out.println("数据一致，不更新权限");
        }else if(office!=null&&_office!=null
                &&office.size()==_office.size()
                &&office.containsAll(_office)){//完全一致
            System.out.println("数据一致，不更新权限");
        }else{
            sysRoleDao.deleteOfficeByRoleId(sysRole.getId());
            if(office.size()>0){//原来有授权，现在取消授权的情况，只删除，不插入
                sysRoleDao.insertRoleOffices(sysRole.getId(),office);
            }

        }
        return 1;

    }
}
