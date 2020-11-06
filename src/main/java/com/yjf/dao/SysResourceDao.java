package com.yjf.dao;

import com.yjf.entity.SysResource;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author 余俊锋
 * @date 2020/11/5 14:02
 * @Description
 */
@org.apache.ibatis.annotations.Mapper
public interface SysResourceDao extends Mapper<SysResource> {
    @Select("select sre.* from sys_role_resource srr,sys_resource sre where srr.role_id=#{roleId}  " +
            "and sre.del_flag='0' " +
            "and srr.resource_id=sre.id")
    List<SysResource>  SelectByRoleId(@Param("roleId") Integer roleId);

    @Select("SELECT DISTINCT\n" +
            "\tsys_resource.id,\n" +
            "\tsys_resource.`name`,\n" +
            "\tsys_resource.common,\n" +
            "\tsys_resource.icon,\n" +
            "\tsys_resource.sort,\n" +
            "\tsys_resource.parent_id,\n" +
            "\tsys_resource.type,\n" +
            "\tsys_resource.url,\n" +
            "\tsys_resource.description,\n" +
            "\tsys_resource.`status`,\n" +
            "\tsys_resource.parent_ids,\n" +
            "\tsys_resource.create_date,\n" +
            "\tsys_resource.update_date,\n" +
            "\tsys_resource.create_by,\n" +
            "\tsys_resource.update_by,\n" +
            "\tsys_resource.del_flag,\n" +
            "\tsys_resource.permission_str \n" +
            "FROM\n" +
            "\tsys_user_role,\n" +
            "\tsys_role_resource,\n" +
            "\tsys_resource \n" +
            "WHERE\n" +
            "\tsys_user_role.user_id = #{userId} \n" +
            "\tAND sys_resource.del_flag = '0' \n" +
            "\tAND sys_resource.type = '0' \n" +
            "\tAND sys_user_role.role_id = sys_role_resource.role_id \n" +
            "\tAND sys_role_resource.resource_id = sys_resource.id")
    List<SysResource> selectByUserId(@Param("userId") Integer userId);

    @Select("select \tsys_resource.* from sys_resource where del_flag='0' and\n" +
            "common='0' and url <> ''")
    List<SysResource> selectCommonResources();


    @Select("SELECT\n" +
            "\tsr.*,\n" +
            "\tsr2.NAME parent_name \n" +
            "FROM\n" +
            "\tsys_resource sr\n" +
            "\tLEFT JOIN sys_resource sr2 ON sr.parent_id = sr2.id \n" +
            "WHERE\n" +
            "\tsr.del_flag = '0' and sr.name like CONCAT('%',#{name},'%')")
    List<SysResource> selectPage(@Param("name") String name);
}
