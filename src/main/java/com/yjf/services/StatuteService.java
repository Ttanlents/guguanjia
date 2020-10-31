package com.yjf.services;

import com.github.pagehelper.PageInfo;
import com.yjf.entity.Statute;

/**
 * @author 余俊锋
 * @date 2020/10/29 17:04
 * @Description
 */
public interface StatuteService extends BaseService<Statute,Integer> {
    public PageInfo<Statute> selectPage(Integer pageNum,Integer pageSize,Statute statute);
}
