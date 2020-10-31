package com.yjf.Test;

import com.yjf.GuguanjiaApplication;
import com.yjf.dao.SysAreaDao;
import com.yjf.entity.SysArea;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Map<String, Object> map = new HashMap<>();
        map.put("name","区");
        List<SysArea> areaList = sysAreaDao.selectPage(map);
        areaList.forEach(n->{
            System.out.println(n);
        });
    }

}
