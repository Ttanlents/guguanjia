package com.yjf.dao;

import com.yjf.entity.SysArea;
import com.yjf.provider.SysAreaProvider;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author 余俊锋
 * @date 2020/10/30 12:42
 * @Description
 */
@org.apache.ibatis.annotations.Mapper
public interface SysAreaDao extends Mapper<SysArea> {
    @SelectProvider(value = SysAreaProvider.class,method = "selectPage")
    public List<SysArea> selectPage(Map<String,Object> map);
}
