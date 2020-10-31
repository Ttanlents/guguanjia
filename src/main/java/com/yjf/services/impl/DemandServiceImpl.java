package com.yjf.services.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yjf.entity.Demand;
import com.yjf.services.DemandService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 余俊锋
 * @date 2020/10/24 10:05
 * @Description
 */
@Service
@Transactional
public class DemandServiceImpl extends BaseServiceImpl<Demand,Integer> implements DemandService {


    @Override
    public PageInfo<Demand> selectPage(Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Demand> demands = mapper.selectAll();
        PageInfo<Demand> pageInfo = new PageInfo<>(demands);
        return pageInfo;
    }
}
