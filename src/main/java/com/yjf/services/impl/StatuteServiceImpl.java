package com.yjf.services.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yjf.entity.Statute;
import com.yjf.services.StatuteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author 余俊锋
 * @date 2020/10/29 17:05
 * @Description
 */
@Service
@Transactional
public class StatuteServiceImpl extends BaseServiceImpl<Statute,Integer> implements StatuteService {

    @Autowired
    Mapper<Statute> mapper;
    @Override
    public PageInfo<Statute> selectPage(Integer pageNum,Integer pageSize,Statute statute) {
        statute.setDelFlag("0");
        PageHelper.startPage(pageNum,pageSize);
        List<Statute> statuteList = mapper.select(statute);
        PageInfo<Statute> pageInfo = new PageInfo<>(statuteList);
        return pageInfo;
    }
}
