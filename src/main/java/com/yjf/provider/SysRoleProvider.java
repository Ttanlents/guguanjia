package com.yjf.provider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @author 余俊锋
 * @date 2020/11/4 13:17
 * @Description
 */
public class SysRoleProvider {

    public String selectPage(Map<String, Object> map) {
        String sql = new SQL() {{
            SELECT("sr.*, " +
                    "so.`name` office_name from sys_role sr " +
                    "LEFT JOIN sys_office so ON sr.office_id = so.id ");
            WHERE("sr.del_flag='0' ");
            if (map.containsKey("dataScope") && !StringUtils.isEmpty(map.get("dataScope"))) {
                WHERE("sr.data_scope=#{dataScope} ");
            }
            if (map.containsKey("officeId") && !StringUtils.isEmpty(map.get("officeId"))) {
                WHERE("sr.office_id=#{officeId} ");
            }
            if (map.containsKey("name") && !StringUtils.isEmpty(map.get("name"))) {
                WHERE("sr.`name` like CONCAT('%',#{name},'%') ");
            }
            if (map.containsKey("remarks") && !StringUtils.isEmpty(map.get("remarks"))) {
                WHERE("sr.remarks like CONCAT('%',#{remarks},'%') ");
            }
        }}.toString();
        return sql;
    }


    public String deleteBatch(@Param("roleId") int roleId, @Param("ids") int[] ids) {

        StringBuilder sb = new StringBuilder();
        sb.append("delete from sys_user_role where role_id=#{roleId} and user_id in (");
        for (int i = 0; i < ids.length; i++) {
            if (i == ids.length - 1) {
                sb.append("#{ids[" + i + "]})");
                break;
            }
            sb.append("#{ids[" + i + "]},");
        }

        return sb.toString();

    }

    public String insertBatch(@Param("roleId") Integer roleId, @Param("userIds") List<Integer> userIds) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO `sys_user_role`( `role_id`, `user_id`, `create_by`, `create_date`, " +
                "`update_by`, `update_date`, `del_flag`) VALUES ");

        for (int i = 0; i < userIds.size(); i++) {
            sb.append("(#{roleId},#{userIds[" + i + "]},null,now(),null,now(),0),");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public String insertRoleResources(@Param("roleId") Integer roleId, @Param("resources") List<Integer> resources) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO sys_role_resource ( `id`, `role_id`, `resource_id`, `create_by`, `create_date`, `update_by`, `update_date`, `del_flag` )\n" +
                "        VALUES ");
        for (int i = 0; i < resources.size(); i++) {
            sb.append("( DEFAULT, #{roleId}, #{resources["+i+"]}, NULL, NOW(), NULL, NOW(), '0' ),");
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }
    public String insertRoleOffices(@Param("roleId") Integer roleId, @Param("offices") List<Integer> offices) {
        StringBuilder sb = new StringBuilder();
       sb.append("INSERT INTO sys_role_office ( `role_id`, `office_id`, `id`, `create_by`, `create_date`, `update_by`, `update_date`, `del_flag` )\n" +
               "VALUES ");
        for (int i = 0; i < offices.size(); i++) {
            sb.append("( #{roleId}, #{offices["+i+"]}, DEFAULT, NULL, NOW(), NULL, NOW(), '0' ),");
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }


}
