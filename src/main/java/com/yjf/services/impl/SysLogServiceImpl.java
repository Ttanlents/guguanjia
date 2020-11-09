package com.yjf.services.impl;

import com.github.pagehelper.PageInfo;
import com.yjf.entity.SysLog;
import com.yjf.services.SysLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 余俊锋
 * @date 2020/11/9 13:18
 * @Description
 */
@Service
@Transactional
public class SysLogServiceImpl extends BaseServiceImpl<SysLog,Integer> implements SysLogService {
    @Override
    public PageInfo<SysLog> selectPage(Integer pageNum, Integer pageSize) {
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int insertSelective(SysLog sysLog) {
        return super.insertSelective(sysLog);
    }
}
