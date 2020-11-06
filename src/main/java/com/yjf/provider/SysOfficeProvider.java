package com.yjf.provider;

import com.yjf.entity.SysOffice;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

/**
 * @author 余俊锋
 * @date 2020/11/2 19:48
 * @Description
 */
public class SysOfficeProvider {

    public String selectSysOffice(SysOffice sysOffice){
        String sql = new SQL() {{
            SELECT("so.*," +
                    "sa.`name` area_name " +
                    "FROM " +
                    "sys_office so " +
                    "LEFT JOIN sys_area sa  " +
                    "on " +
                    "so.area_id = sa.id  ");
            if (!StringUtils.isEmpty(sysOffice.getName())){
                WHERE("so.`name` like CONCAT('%',#{name},'%')");
            }


        }}.toString();
        return sql;
    }
}
