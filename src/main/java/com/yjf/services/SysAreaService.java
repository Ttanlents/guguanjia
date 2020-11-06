package com.yjf.services;

import com.github.pagehelper.PageInfo;
import com.yjf.entity.SysArea;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * @author 余俊锋
 * @date 2020/10/30 12:43
 * @Description
 */
public interface SysAreaService extends BaseService<SysArea,Integer> {
     PageInfo<SysArea> selectPage(Integer pageNum, Integer pageSize, Map<String,Object> map);

    void insertForeach(List<SysArea> sysAreas);

    int updateByParentId(Map<String,Object> map);

    void downloadExcel(OutputStream os,List<SysArea> list);
    void uploadExcel(InputStream is);


}
