package com.yjf.provider;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author 余俊锋
 * @date 2020/10/30 12:45
 * @Description
 */
public class SysAreaProvider {
    /**
     *@Description TODO:根据条件查询   区域的sid    区域名  name
     *@author 余俊锋
     *@date 2020/10/30 12:53
     *@params map
     *@return java.lang.String
     */
    public String selectPage(Map<String,Object> map){
        String sql=new SQL(){{
            SELECT("sa.*, " +
                    "parent.`name` parent_name  " +
                    "FROM  " +
                    "sys_area sa " +
                    "LEFT JOIN sys_area parent ON sa.parent_id = parent.id  ");
            WHERE("sa.del_flag = '0' ");
            if (map.containsKey("sid")&& !StringUtils.isEmpty(map.get("sid"))){
                WHERE("FIND_IN_SET(#{sid},sa.parent_ids) or sa.id=#{sid}");
            }else if (map.containsKey("name")&& !StringUtils.isEmpty(map.get("name"))){

                WHERE("sa.`name` like CONCAT('%',#{name},'%')");
            }
        }}.toString();
        return sql;
    }
}
