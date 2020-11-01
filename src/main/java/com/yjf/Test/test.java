package com.yjf.Test;

import com.yjf.GuguanjiaApplication;
import com.yjf.dao.SysAreaDao;
import com.yjf.entity.SysArea;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 余俊锋
 * @date 2020/10/22 13:35
 * @Description
 */
@SpringBootTest(classes = GuguanjiaApplication.class)
public class test {
    @Autowired
    SysAreaDao sysAreaDao;

    @Test
    public void myTest(){
        SysArea sysArea = new SysArea();
        sysArea.setDelFlag("0");
        sysArea.setCode("10086");
        sysArea.setParentIds("0,1,3,5");
        sysArea.setCreateBy("admin");
        sysArea.setCreateDate(new Date());
        sysArea.setIcon("fa fa-institution");
        sysArea.setName("广西");
        sysArea.setUpdateBy("admin");
        sysArea.setUpdateDate(new Date());
        sysArea.setRemarks("");
        sysArea.setType("1");
        sysArea.setParentId(3);
        SysArea sysArea1 = new SysArea();
        sysArea1.setDelFlag("0");
        sysArea1.setCode("10086");
        sysArea1.setParentIds("0,1,3,5");
        sysArea1.setCreateBy("admin");
        sysArea1.setCreateDate(new Date());
        sysArea1.setIcon("fa fa-institution");
        sysArea1.setName("广西");
        sysArea1.setUpdateBy("admin");
        sysArea1.setUpdateDate(new Date());
        sysArea1.setRemarks("");
        sysArea1.setType("1");
        sysArea1.setParentId(3);
        List<SysArea> list=new ArrayList<>();
        list.add(sysArea);
        list.add(sysArea1);
        sysAreaDao.insertForeach(list);
        for (SysArea area : list) {
            Integer id = area.getId();
            System.out.println(id);
        }

    }

}
