package com.yjf.services.impl;

import com.yjf.entity.SysOffice;
import com.yjf.services.SysOfficeService;
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

    @Override
    @Cacheable(cacheNames = "sysOfficeCache",key = "'com.yjf.services.impl.SysOfficeServiceImpl:select'+#sysOffice.delFlag")
    public List<SysOffice> select(SysOffice sysOffice) {
        sysOffice.setDelFlag("0");
        return super.select(sysOffice);
    }
}
