package com.yjf.services;

import com.github.pagehelper.PageInfo;
import com.yjf.entity.Qualification;
import com.yjf.entity.QualificationCondition;

/**
 * @author 余俊锋
 * @date 2020/10/26 12:00
 * @Description
 */
public interface QualificationService extends BaseService<Qualification,Integer> {

    public PageInfo<Qualification> selectPage(Integer pageNum, Integer pageSize, QualificationCondition condition);
}
