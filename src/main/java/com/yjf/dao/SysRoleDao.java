package com.yjf.dao;

import com.yjf.entity.SysRole;
import com.yjf.provider.SysRoleProvider;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author 余俊锋
 * @date 2020/11/4 13:17
 * @Description
 */
@org.apache.ibatis.annotations.Mapper
public interface SysRoleDao extends Mapper<SysRole> {
    @SelectProvider(value = SysRoleProvider.class,method = "selectPage")
    List<SysRole> selectPage(Map<String,Object> map);

    @DeleteProvider(value = SysRoleProvider.class,method = "deleteBatch")
    int deleteBatch(@Param("roleId") int roleId,@Param("ids") int[] ids);

    @InsertProvider(value = SysRoleProvider.class,method = "insertBatch" )
    int insertBatch(@Param("roleId")Integer roleId,@Param("userIds") List<Integer> userIds);

    @InsertProvider(value = SysRoleProvider.class,method = "insertRoleResources")
    int insertRoleResources(@Param("roleId") Integer roleId, @Param("resources") List<Integer> resources);

    @InsertProvider(value = SysRoleProvider.class,method = "insertRoleOffices")
    int insertRoleOffices(@Param("roleId") Integer roleId, @Param("offices") List<Integer> offices);

    @Delete("DELETE from sys_role_resource WHERE role_id=#{id}")
    int deleteResourcesByRoleId(@Param("id") Integer id);

    @Delete("DELETE from sys_role_resource WHERE role_id=#{id}")
    int deleteOfficeByRoleId(@Param("id") Integer id);

    @Select("select sys_resource.name from sys_role_resource,sys_resource where sys_role_resource.role_id=#{roleId} and sys_role_resource.resource_id=sys_resource.id")
    List<String> selectAuthorityByRoleId(@Param("roleId") Integer roleId);

    @Select("select sys_user.name from sys_user_role,sys_user where sys_user_role.role_id=#{roleId} and sys_user_role.user_id=sys_user.id")
    List<String> selectAssignmentUserByRoleId(@Param("roleId") Integer roleId);
}
