package com.yjf.provider;

import com.yjf.entity.SysLog;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

/**
 * @author 余俊锋
 * @date 2020/11/11 19:03
 * @Description
 */
public class SysLogProvider {

    public String selectPage(SysLog sysLog){
        return  new SQL(){{
            SELECT("sys_log.id,\n" +
                    "sys_log.type,\n" +
                    "sys_log.create_by,\n" +
                    "sys_log.create_date,\n" +
                    "sys_log.remote_addr,\n" +
                    "sys_log.user_agent,\n" +
                    "sys_log.request_uri,\n" +
                    "sys_log.method,\n" +
                    "sys_log.params,\n" +
                    "sys_log.exception,\n" +
                    "sys_log.description\n" +
                    "FROM\n" +
                    "sys_log ");
            if (!StringUtils.isEmpty(sysLog.getType())){
                WHERE("sys_log.type = #{type} ");
            }
            if (!StringUtils.isEmpty(sysLog.getDescription())){
                WHERE("sys_log.description like concat('%',#{description},'%') ");
            }
        }}.toString();
    }

}
