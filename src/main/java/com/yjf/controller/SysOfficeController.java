package com.yjf.controller;

import com.yjf.entity.Result;
import com.yjf.entity.SysOffice;
import com.yjf.services.SysOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author 余俊锋
 * @date 2020/10/27 17:11
 * @Description
 */
@Controller
@RequestMapping(value = "sysOffice")
public class SysOfficeController {
    @Autowired
    SysOfficeService sysOfficeService;

    @ResponseBody
    @RequestMapping(value = "select")
    public Result select(){
        Result result = new Result();
        List<SysOffice> list = sysOfficeService.select(new SysOffice());
        result.setObj(list);
        return result;
    }
}
