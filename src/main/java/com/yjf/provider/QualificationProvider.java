package com.yjf.provider;

import com.yjf.entity.QualificationCondition;
import org.springframework.util.StringUtils;

/**
 * @author 余俊锋
 * @date 2020/10/26 10:29
 * @Description
 */
public class QualificationProvider {

    public String selectPage(QualificationCondition condition){
       StringBuilder sb=new StringBuilder();
       sb.append("SELECT " +
               "qu.*, " +
               "uu.`name` upload_name, " +
               "cu.NAME check_name " +
               "FROM " +
               "qualification qu " +
               "LEFT JOIN sys_user uu ON qu.upload_user_id = uu.id " +
               "LEFT JOIN sys_user cu ON qu.check_user_id = cu.id  " +
               "WHERE " +
               "qu.del_flag = 0 ");
       if (!StringUtils.isEmpty(condition.getStartDate())){
           sb.append("and qu.create_date >= #{startDate} ");
       }
        if (!StringUtils.isEmpty(condition.getEndDate())){
            sb.append("and qu.create_date <= #{endDate} ");
        }
        if (!StringUtils.isEmpty(condition.getType())){
            sb.append("and qu.type = #{type} ");
        }
        if (!StringUtils.isEmpty(condition.getCheck())){
            sb.append("and qu.check = #{check} ");
        }
        return sb.toString();


    }
}
