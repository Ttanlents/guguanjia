package com.yjf.provider;

import com.yjf.entity.SysUser;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

/**
 * @author 余俊锋
 * @date 2020/11/3 16:08
 * @Description
 */
public class SysUserProvider {
    public String selectPage(SysUser sysUser){
        String sql = new SQL() {{
            SELECT("DISTINCT su.*, " +
                    "so.`name` office_name," +
                    "sr.id role_id," +
                    "sr.`name` role_name " +
                    "FROM " +
                    "sys_user su " +
                    "LEFT JOIN sys_office so ON su.office_id = so.id  " +
                    "left join sys_role_office sro on so.id=sro.office_id " +
                    "left join sys_role sr on sr.id=sro.role_id  ");
            WHERE("su.del_flag = '0' ");
            if (!StringUtils.isEmpty(sysUser.getOfficeId())){
                WHERE("su.office_id=#{officeId} ");
            }
            if (!StringUtils.isEmpty(sysUser.getRoleId())){
                WHERE("sr.id=#{roleId} ");
            }
            if (!StringUtils.isEmpty(sysUser.getName())){
                WHERE("su.name like CONCAT('%',#{name},'%') ");
            }
            if (!StringUtils.isEmpty(sysUser.getNo())){
                WHERE("su.`no`=#{no}");
            }
        }}.toString();
        return sql;
    }

    public String selectRole(SysUser sysUser){
        String sql = new SQL() {{
            SELECT("sys_user.*,sys_user_role.role_id  " +
                    "FROM " +
                    "sys_user " +
                    "LEFT JOIN sys_user_role ON sys_user.id = sys_user_role.user_id  ");
            WHERE("sys_user.del_flag='0' ");
            if (!StringUtils.isEmpty(sysUser.getOfficeId())){
                WHERE("sys_user.office_id=#{officeId} ");
            }
            if (!StringUtils.isEmpty(sysUser.getRoleId())){
                WHERE("sys_user_role.role_id =#{roleId}");
            }


        }}.toString();
        return sql;
    }
}
