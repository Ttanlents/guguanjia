package com.yjf.services.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yjf.dao.SysAreaDao;
import com.yjf.entity.SysArea;
import com.yjf.services.SysAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author 余俊锋
 * @date 2020/10/30 12:44
 * @Description
 */
@Service
@Transactional
public class SysAreaServiceImpl extends BaseServiceImpl<SysArea,Integer> implements SysAreaService {
   @Autowired
    SysAreaDao sysAreaDao;

    @Override
    public PageInfo<SysArea> selectPage(Integer pageNum, Integer pageSize, Map<String, Object> map) {
        PageHelper.startPage(pageNum,pageSize);
        List<SysArea> sysAreas = sysAreaDao.selectPage(map);
        PageInfo<SysArea> pageInfo = new PageInfo<>(sysAreas);
        return pageInfo;
    }

    @Override
    public List<SysArea> select(SysArea sysArea) {
        sysArea.setDelFlag("0");
        return super.select(sysArea);
    }
}
