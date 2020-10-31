package com.yjf.services.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yjf.dao.WorkOrderDao;
import com.yjf.entity.WorkOrder;
import com.yjf.services.WorkOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author 余俊锋
 * @date 2020/10/28 11:30
 * @Description
 */
@Service
@Transactional
public class WorkOrderServiceImpl extends BaseServiceImpl<WorkOrder,Integer> implements WorkOrderService {
    @Autowired
    WorkOrderDao workOrderDao;


    @Override
    public PageInfo<WorkOrder> selectPage(Integer pageNum, Integer pageSize, Map<String, String> map) {
        PageHelper.startPage(pageNum,pageSize);
        List<WorkOrder> workOrders = workOrderDao.selectPage(map);
        PageInfo<WorkOrder> pageInfo = new PageInfo<>(workOrders);
        return pageInfo;
    }


    public Map<String,Object> selectById(Integer id) {
       return workOrderDao.selectById(id);
    }
}
