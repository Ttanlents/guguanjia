package com.yjf.services.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yjf.dao.SysLogDao;
import com.yjf.entity.SysLog;
import com.yjf.services.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 余俊锋
 * @date 2020/11/9 13:18
 * @Description
 */
@Service
@Transactional
public class SysLogServiceImpl extends BaseServiceImpl<SysLog,Integer> implements SysLogService {
    @Autowired
    SysLogDao sysLogDao;
    @Override
    public PageInfo<SysLog> selectPage(Integer pageNum, Integer pageSize,SysLog sysLog) {
        PageHelper.startPage(pageNum,pageSize);
        List<SysLog> sysLogs = sysLogDao.selectPage(sysLog);
        PageInfo<SysLog> pageInfo = new PageInfo<>(sysLogs);
        return pageInfo;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int insertSelective(SysLog sysLog) {
        return super.insertSelective(sysLog);
    }
}
