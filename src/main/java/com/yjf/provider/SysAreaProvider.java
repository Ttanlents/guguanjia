package com.yjf.provider;

import com.yjf.entity.SysArea;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.List;
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

    public String insertBatch(Map<String, List<SysArea>> map){
        List<SysArea> list = map.get("list");   //sql中的集合名字只能叫list  否则就要使用@params注解
        String sql = new SQL() {{
            INSERT_INTO("sys_area");
            for (int i = 0; i < list.size(); i++) {
                StringBuilder sb = new StringBuilder();
                sb.append("default,").append("#{list["+i+"].parentId},").append("#{list["+i+"].parentIds},").
                        append("#{list["+i+"].code},").append("#{list["+i+"].name},").
                        append("#{list["+i+"].type},").append("#{list["+i+"].createBy},").append("#{list["+i+"].createDate},").
                        append("#{list["+i+"].updateBy},").append("#{list["+i+"].updateDate},").append("#{list["+i+"].remarks},").
                        append("#{list["+i+"].delFlag},").append("#{list["+i+"].icon}");
                INTO_VALUES(sb.toString());
                ADD_ROW();//动态添加一个()
            }
        }}.toString();
        return sql;
    }


}
