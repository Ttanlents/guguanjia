package com.yjf.dao;

import com.yjf.entity.Qualification;
import com.yjf.entity.QualificationCondition;
import com.yjf.provider.QualificationProvider;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author 余俊锋
 * @date 2020/10/26 11:33
 * @Description
 */
@org.apache.ibatis.annotations.Mapper
public interface QualificationDao extends Mapper<Qualification> {

    @SelectProvider(value = QualificationProvider.class,method = "selectPage")
    public List<Qualification> selectpage(QualificationCondition condition);
}
