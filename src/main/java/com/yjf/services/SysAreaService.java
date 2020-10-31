package com.yjf.services;

import com.github.pagehelper.PageInfo;
import com.yjf.entity.SysArea;

import java.util.Map;

/**
 * @author 余俊锋
 * @date 2020/10/30 12:43
 * @Description
 */
public interface SysAreaService extends BaseService<SysArea,Integer> {
    public PageInfo<SysArea> selectPage(Integer pageNum, Integer pageSize, Map<String,Object> map);


}
