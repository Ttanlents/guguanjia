package com.yjf.dao;

import com.yjf.entity.SysUser;
import com.yjf.provider.SysUserProvider;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author 余俊锋
 * @date 2020/10/26 15:48
 * @Description
 */
@org.apache.ibatis.annotations.Mapper
public interface SysUserDao extends Mapper<SysUser> {
    @SelectProvider(value = SysUserProvider.class,method = "selectPage")
    List<SysUser> selectPage(SysUser sysUser);

    @SelectProvider(value = SysUserProvider.class,method = "selectRole")
    List<SysUser> selectRole(SysUser sysUser);

    @Select("SELECT\n" +
            "\tsus.*\n" +
            "FROM\n" +
            "\tsys_user sus \n" +
            "WHERE\n" +
            "\tsus.office_id = #{officeId} \n" +
            "\tAND NOT EXISTS (\n" +
            "\tSELECT\n" +
            "\t\t1 \n" +
            "FROM\n" +
            "\tsys_user_role sur where sur.role_id=#{roleId} and sus.id=sur.user_id)")
    List<SysUser> selectNoRole(@Param("officeId")Integer officeId, @Param("roleId") Integer roleId);


    @Update("update sys_user_role set role_id=#{roleId}  where user_id=#{userId}")
    int updateRoleByUserId(@Param("userId")Integer userId, @Param("roleId") String roleId);

    @Insert("insert into sys_user_role VALUES(DEFAULT,#{roleId},#{userId},#{createBy},NOW(),#{createBy},NOW(),'0')")
    int insertUserRole(@Param("userId")Integer userId, @Param("roleId") String roleId,@Param("createBy") String createBy);

}
