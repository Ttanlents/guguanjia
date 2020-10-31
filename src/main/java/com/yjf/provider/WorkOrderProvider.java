package com.yjf.provider;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author 余俊锋
 * @date 2020/10/28 10:29
 * @Description
 */
public class WorkOrderProvider {
    public String selectPage(Map<String,Object> map){
        return new SQL(){{
            SELECT("wo.*," +
                    "cu.`name` create_user_name," +
                    "tu.`name` transport_user_name," +
                    "ru.`name` recipient_user_name," +
                    "co.`name` create_office_name");
            FROM("work_order wo");
            LEFT_OUTER_JOIN("sys_user cu ON wo.create_user_id = cu.id");
            LEFT_OUTER_JOIN("sys_user tu ON wo.transport_user_id = tu.id");
            LEFT_OUTER_JOIN("sys_user ru ON wo.recipient_user_id = ru.id");
            LEFT_OUTER_JOIN("sys_office co ON cu.office_id = co.id");
            LEFT_OUTER_JOIN("sys_office `to` ON tu.office_id = `to`.id");
            LEFT_OUTER_JOIN("sys_office ro ON ru.office_id = ro.id ");
            WHERE("wo.del_flag = '0'");
            if (map.containsKey("status")&&!StringUtils.isEmpty(map.get("status"))){
                WHERE("wo.`status` = #{status}");
            }
            if (map.containsKey("startTime")&&!StringUtils.isEmpty(map.get("startTime"))){
                WHERE("wo.create_date >= #{startTime}");
            }
            if (map.containsKey("endTime")&&!StringUtils.isEmpty(map.get("endTime"))){
                WHERE("wo.create_date <= #{endTime}");
            }
            if (map.containsKey("id")&&!StringUtils.isEmpty(map.get("id"))){
                WHERE("(co.id = #{id} OR `to`.id = #{id} OR ro.id = #{id})");
            }
        }}.toString();

    }


    public String selectById(Integer id){
        String sql="SELECT\n" +
                "\two.*,\n" +
                "\tcu.`name` create_user_name,\n" +
                "\tcu.phone create_user_phone,\n" +
                "\ttu.`name` transport_user_name,\n" +
                "\ttu.phone transport_user_phone,\n" +
                "\tru.`name` recipient_user_name,\n" +
                "\tru.phone recipient_user_phone,\n" +
                "\tco.`name` create_office_name,\n" +
                "\t`to`.`name` transport_office_name,\n" +
                "\tro.`name` recipient_office_name\n" +
                "\tfrom\n" +
                "\twork_order wo\n" +
                "\tleft join sys_user cu ON wo.create_user_id = cu.id\n" +
                "\tleft join sys_user tu ON wo.transport_user_id = tu.id\n" +
                "\tleft join sys_user ru ON wo.recipient_user_id = ru.id\n" +
                "\tleft join sys_office co ON cu.office_id = co.id\n" +
                "\tleft join sys_office `to` ON tu.office_id = `to`.id\n" +
                "\tleft join sys_office ro ON ru.office_id = ro.id\n" +
                "where wo.del_flag = '0' and wo.id=#{id}";
        return sql;
    }
}
