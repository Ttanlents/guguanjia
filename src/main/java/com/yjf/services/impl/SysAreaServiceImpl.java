package com.yjf.services.impl;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yjf.Listener.SysAreaListener;
import com.yjf.dao.SysAreaDao;
import com.yjf.entity.SysArea;
import com.yjf.services.SysAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    public void insertForeach(List<SysArea> sysAreas) {
        sysAreaDao.insertForeach(sysAreas);
    }

    @Override
    public int updateByParentId(Map<String, Object> map) {
        int result=0;
        if (map.containsKey("area")&&!StringUtils.isEmpty(map.get("area"))){
            SysArea area =(SysArea) map.get("area");
            area.setUpdateDate(new Date());
            result+= sysAreaDao.updateByPrimaryKeySelective(area);
            if (map.containsKey("oldParentIds")&&!StringUtils.isEmpty(map.get("oldParentIds"))){
                String oldParentIds = (String)map.get("oldParentIds");
                String parentIds = area.getParentIds();
                if (!Objects.equals(oldParentIds,parentIds)){
                    Integer parentId = area.getId();
                    result+=sysAreaDao.updateByParentId(oldParentIds,parentIds,parentId.toString());
                }
            }
        }
        return result;
    }

    @Override
    public void downloadExcel(OutputStream os,List<SysArea> list) {
        EasyExcel.write(os,SysArea.class).sheet().doWrite(list);
    }

    @Override
    public void uploadExcel(InputStream is) {
        EasyExcel.read(is,SysArea.class,new SysAreaListener(sysAreaDao)).sheet().doRead();
    }


    @Override
    public List<SysArea> select(SysArea sysArea) {
        sysArea.setDelFlag("0");
        return super.select(sysArea);
    }
}
