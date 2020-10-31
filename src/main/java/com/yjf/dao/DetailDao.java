package com.yjf.dao;

import com.yjf.entity.Detail;
import com.yjf.provider.DetailProvider;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author 余俊锋
 * @date 2020/10/28 17:30
 * @Description
 */
@org.apache.ibatis.annotations.Mapper
public interface DetailDao extends Mapper<Detail> {
    @SelectProvider(value = DetailProvider.class,method = "selectDetail")
    public List<Map<String,String>> selectDetail(Integer id);
}
