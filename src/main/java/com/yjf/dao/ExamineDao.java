package com.yjf.dao;

import com.yjf.entity.Examine;
import com.yjf.provider.ExamineProvider;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author 余俊锋
 * @date 2020/10/27 15:20
 * @Description
 */
@org.apache.ibatis.annotations.Mapper
public interface ExamineDao extends Mapper<Examine> {

    @SelectProvider(value = ExamineProvider.class,method = "selectPage")
    public List<Examine> selectPage(Map<String,String> map);


}
