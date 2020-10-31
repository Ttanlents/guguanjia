package com.yjf.services.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yjf.dao.ExamineDao;
import com.yjf.entity.Examine;
import com.yjf.services.ExamineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author 余俊锋
 * @date 2020/10/27 15:35
 * @Description
 */
@Service
@Transactional
public class ExamineServiceImpl extends BaseServiceImpl<Examine,Integer> implements ExamineService {

    @Autowired
    ExamineDao examineDao;
    @Override
    public PageInfo<Examine> selectPage(Integer pageNum, Integer pageSize, Map<String,String> map) {
        PageHelper.startPage(pageNum,pageSize);
        List<Examine> examineList = examineDao.selectPage(map);
        PageInfo<Examine> pageInfo = new PageInfo<>(examineList);
        return pageInfo;
    }
}
