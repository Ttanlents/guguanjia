package com.yjf.provider;

import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author 余俊锋
 * @date 2020/10/27 15:08
 * @Description
 */
public class ExamineProvider {
    
    public String selectPage(Map<String,String> map){
        StringBuilder sb=new StringBuilder();
        sb.append("SELECT " +
                "e.*, " +
                "su.`name`, " +
                "so.`name` AS company_name  " +
                "FROM " +
                "`examine` e, " +
                "sys_user su, " +
                "sys_office so  " +
                "WHERE " +
                "e.del_flag = '0' ");
        if (map.containsKey("type")&&!StringUtils.isEmpty(map.get("type"))){
            sb.append("AND e.type =#{type} ");
        }
        if (map.containsKey("oid")&&!StringUtils.isEmpty(map.get("oid"))){
            sb.append("AND su.office_id = #{oid} ");
        }
        if (map.containsKey("name")&&!StringUtils.isEmpty(map.get("name"))){
            sb.append("AND su.`name` LIKE CONCAT( '%',#{name}, '%' )  ");
        }
        sb.append("AND e.examine_user_id = su.id  " +
                "AND su.office_id = so.id");
        return sb.toString();
    }
}
