package com.yjf.services;

import com.github.pagehelper.PageInfo;
import com.yjf.entity.WorkOrder;

import java.util.Map;

/**
 * @author 余俊锋
 * @date 2020/10/28 11:30
 * @Description
 */
public interface WorkOrderService extends BaseService<WorkOrder,Integer> {
    public PageInfo<WorkOrder> selectPage(Integer pageNum, Integer pageSize, Map<String,String> map);
    public Map<String,Object> selectById(Integer id);
}
