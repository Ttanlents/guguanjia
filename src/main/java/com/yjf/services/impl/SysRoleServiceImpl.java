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
import org.springframework.util.StringUtils;

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
            _resources=( List<Integer>) map.get("_resources");  //旧的
        }


        //第一步  为角色分配菜单权限 表：sys_role_resource
        if(resources==null&&_resources==null){
            //不更新
            System.out.println("数据一致，不更新权限");
        }else if(resources!=null&&_resources!=null
                &&resources.size()==_resources.size()
                &&resources.containsAll(_resources)){//完全一致
            System.out.println("数据一致，不更新权限");
        }else{
            sysRoleDao.deleteResourcesByRoleId(sysRole.getId());
            if(resources.size()>0){//原来有授权，现在取消授权的情况，只删除，不插入
                sysRoleDao.insertRoleResources(sysRole.getId(),resources);
            }

        }



        List<Integer> office = null;
        if(map.containsKey("offices")){
            office = (List<Integer>) map.get("offices");
        }
        List<Integer> _office = null;
        if(map.containsKey("_offices")){
            _office = (List<Integer>) map.get("_offices");
        }


        //第二步  分角色 夸机构授权 表：sys_role_office
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

    @Override
    public List<String> selectAuthorityByRoleId(Integer roleId) {
        return sysRoleDao.selectAuthorityByRoleId(roleId);
    }

    @Override
    public List<String> selectAssignmentUserByRoleId(Integer roleId) {
        return sysRoleDao.selectAssignmentUserByRoleId(roleId);
    }

    @Override
    public int insertSelective(Map<String,Object> map) {
        int i=0;
        SysRole sysRole=null;
        if (map.containsKey("role")&&!StringUtils.isEmpty(map.get("role"))){
            Object role = map.get("role");
            String json = JsonUtils.pojoToJson(role);
             sysRole = JsonUtils.jsonToPojo(json, SysRole.class);
            sysRole.setCreateDate(new Date());
            i+=sysRoleDao.insertSelective(sysRole);
        }
        List<Integer> resources=null;
        if (map.containsKey("resources")){
            resources=( List<Integer>) map.get("resources");
        }
        List<Integer> office = null;
        if(map.containsKey("offices")){
            office = (List<Integer>) map.get("offices");
        }
        if (resources!=null&&resources.size()>0){
          i+=  sysRoleDao.insertRoleResources(sysRole.getId(),resources);
        }
        if (office!=null&&office.size()>0){
            i+=  sysRoleDao.insertRoleOffices(sysRole.getId(),office);
        }
        return i;

    }
}
