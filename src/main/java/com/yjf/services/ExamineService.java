package com.yjf.services;

import com.github.pagehelper.PageInfo;
import com.yjf.entity.Examine;

import java.util.Map;

/**
 * @author 余俊锋
 * @date 2020/10/27 15:33
 * @Description
 */
public interface ExamineService extends BaseService<Examine,Integer> {
    public PageInfo<Examine> selectPage(Integer pageNum, Integer pageSize, Map<String,String> map);

}
