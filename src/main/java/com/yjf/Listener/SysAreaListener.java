package com.yjf.Listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.yjf.dao.SysAreaDao;
import com.yjf.entity.SysArea;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 余俊锋 TODO:此类不能使用spring容器管理
 * @date 2020/11/2 16:17
 * @Description
 */
public class SysAreaListener extends AnalysisEventListener<SysArea> {

    private List<SysArea> sysAreaList;
    private SysAreaDao SysAreaDao;

    public SysAreaListener(SysAreaDao SysAreaDao) {
        this.sysAreaList = new ArrayList<>();
        this.SysAreaDao=SysAreaDao;
    }

    @Override
    public void invoke(SysArea sysArea, AnalysisContext analysisContext) {
        sysAreaList.add(sysArea);
        if (sysAreaList.size()==10){
            SysAreaDao.insertBatch(sysAreaList);
            sysAreaList.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        if (sysAreaList.size()>0){
            SysAreaDao.insertBatch(sysAreaList);
            sysAreaList.clear();
        }
    }
}
