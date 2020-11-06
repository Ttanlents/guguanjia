package com.yjf.dao;

import com.yjf.entity.SysOffice;
import com.yjf.provider.SysOfficeProvider;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author 余俊锋
 * @date 2020/10/27 17:05
 * @Description
 */
@org.apache.ibatis.annotations.Mapper
public interface SysOfficeDao extends Mapper<SysOffice> {

    @SelectProvider(value = SysOfficeProvider.class,method = "selectSysOffice")
     List<SysOffice> selectSysOffice(SysOffice sysOffice);

    @Select("SELECT\n" +
            "\tsof.* \n" +
            "FROM\n" +
            "\tsys_role_office sro,\n" +
            "\tsys_office sof \n" +
            "WHERE\n" +
            "\tsof.del_flag = '0' \n" +
            "\tAND sro.role_id = #{roleId} \n" +
            "\tAND sro.office_id = sof.id")
    List<SysOffice> selectByRoleId(Integer roleId);


}
