package com.yjf.dao;

import com.yjf.entity.WorkOrder;
import com.yjf.provider.WorkOrderProvider;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author 余俊锋
 * @date 2020/10/28 10:30
 * @Description
 */
@org.apache.ibatis.annotations.Mapper
public interface WorkOrderDao extends Mapper<WorkOrder> {
    @SelectProvider(value = WorkOrderProvider.class,method = "selectPage")
    public List<WorkOrder> selectPage(Map<String,String> map);

    @SelectProvider(value = WorkOrderProvider.class,method = "selectById")
    public Map<String,Object> selectById(Integer id);

}
