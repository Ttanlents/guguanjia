package com.yjf.services.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yjf.dao.SysOfficeDao;
import com.yjf.entity.SysOffice;
import com.yjf.services.SysOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 余俊锋
 * @date 2020/10/27 17:06
 * @Description
 */
@Service
@Transactional
public class SysOfficeServiceImpl extends BaseServiceImpl<SysOffice,Integer> implements SysOfficeService {
    @Autowired
    SysOfficeDao sysOfficeDao;
    @Override
    @Cacheable(cacheNames = "sysOfficeCache",key = "'com.yjf.services.impl.SysOfficeServiceImpl:select'+#sysOffice.delFlag")
    public List<SysOffice> select(SysOffice sysOffice) {
        sysOffice.setDelFlag("0");
        return super.select(sysOffice);
    }

    @Override
    public PageInfo<SysOffice> selectPage(Integer pageNum,Integer pageSize,SysOffice sysOffice) {
        PageHelper.startPage(pageNum,pageSize);
        List<SysOffice> sysOffices = sysOfficeDao.selectSysOffice(sysOffice);
        PageInfo<SysOffice> pageInfo = new PageInfo<>(sysOffices);
        return pageInfo;
    }

    @Override
    public List<SysOffice> selectByRoleId(Integer roleId) {
       return sysOfficeDao.selectByRoleId(roleId);
    }
}
