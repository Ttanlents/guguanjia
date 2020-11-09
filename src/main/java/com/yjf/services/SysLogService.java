package com.yjf.services;

import com.github.pagehelper.PageInfo;
import com.yjf.entity.SysLog;

/**
 * @author 余俊锋
 * @date 2020/10/22 15:19
 * @Description
 */
public interface SysLogService extends BaseService<SysLog,Integer> {

     PageInfo<SysLog> selectPage(Integer pageNum, Integer pageSize);


}
