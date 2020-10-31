package com.yjf.services;

import com.github.pagehelper.PageInfo;
import com.yjf.entity.Demand;

/**
 * @author 余俊锋
 * @date 2020/10/24 10:04
 * @Description
 */
public interface DemandService extends BaseService<Demand,Integer> {

    PageInfo<Demand> selectPage(Integer pageNum, Integer pageSize);
}
