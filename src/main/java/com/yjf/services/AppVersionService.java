package com.yjf.services;

import com.github.pagehelper.PageInfo;
import com.yjf.entity.AppVersion;

/**
 * @author 余俊锋
 * @date 2020/10/22 15:19
 * @Description
 */
public interface AppVersionService extends BaseService<AppVersion,Integer> {

    public PageInfo<AppVersion> selectPage(Integer pageNum, Integer pageSize);
}
