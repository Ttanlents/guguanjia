package com.yjf.Test;

import com.yjf.GuguanjiaApplication;
import com.yjf.dao.SysLogDao;
import com.yjf.entity.SysLog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author 余俊锋
 * @date 2020/10/22 13:35
 * @Description
 */
@SpringBootTest(classes = GuguanjiaApplication.class)
public class test {
    @Autowired
    SysLogDao sysLogDao;

    @Test
    public void myTest(){
       // EasyExcel.write("d:/pic/area.xlsx", SysArea.class).sheet("区域信息").doWrite(sysAreaService.selectAll());
        //EasyExcel.read("d:/pic/区域信息表.xlsx", SysArea.class,new SysAreaListener(sysAreaDao)).sheet().doRead();
        SysLog sysLog = new SysLog();
        sysLog.setDescription(null);
        sysLog.setType("2");
        List<SysLog> select = sysLogDao.select(sysLog);
        System.out.println(select);
    }

}
