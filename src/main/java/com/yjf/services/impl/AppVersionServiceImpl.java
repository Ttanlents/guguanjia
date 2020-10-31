package com.yjf.services.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yjf.entity.AppVersion;
import com.yjf.services.AppVersionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 余俊锋
 * @date 2020/10/22 15:20
 * @Description
 */
@Service
@Transactional
public class AppVersionServiceImpl extends BaseServiceImpl<AppVersion,Integer>
        implements AppVersionService {


    @Override
    public PageInfo<AppVersion> selectPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        AppVersion appVersion = new AppVersion();
        appVersion.setDelFlag("0");
        List<AppVersion> appVersions = mapper.select(appVersion);
        PageInfo<AppVersion> pageInfo = new PageInfo<>(appVersions);
       return  pageInfo;
    }

}
