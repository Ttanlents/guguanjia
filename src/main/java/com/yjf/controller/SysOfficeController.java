package com.yjf.controller;

import com.github.pagehelper.PageInfo;
import com.yjf.entity.Result;
import com.yjf.entity.SysOffice;
import com.yjf.services.SysOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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

    /**
     *@Description TODO:zTree树装结构数据 核心查询
     *@author 余俊锋
     *@date 2020/11/2 19:06
     *@params
     *@return com.yjf.entity.Result
     */
    @ResponseBody
    @RequestMapping(value = "select")
    public Result select(){
        Result result = new Result();
        List<SysOffice> list = sysOfficeService.select(new SysOffice());
        result.setObj(list);
        return result;
    }

    @RequestMapping("selectPage/{pageNum}/{pageSize}")
    @ResponseBody
    public Result selectPage(@PathVariable Integer pageNum, @PathVariable Integer pageSize, SysOffice sysOffice) {
        Result result = new Result();
        PageInfo<SysOffice> pageInfo = sysOfficeService.selectPage(pageNum, pageSize,sysOffice);
        result.setObj(pageInfo);
        return result;
    }
    
    @RequestMapping(value = "toIndex")
    public String toIndex() {
        return "/office/office.html";
    }

}
